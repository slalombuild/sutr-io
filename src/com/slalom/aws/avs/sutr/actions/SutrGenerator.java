package com.slalom.aws.avs.sutr.actions;

import com.google.gson.Gson;
import com.slalom.aws.avs.sutr.actions.constants.PythonText;
import com.slalom.aws.avs.sutr.actions.exceptions.SutrGeneratorException;
import com.slalom.aws.avs.sutr.models.Intent;
import com.slalom.aws.avs.sutr.models.Intents;
import com.slalom.aws.avs.sutr.models.Slot;
import com.slalom.aws.avs.sutr.models.Utterance;
import com.slalom.aws.avs.sutr.psi.*;
import com.slalom.aws.avs.sutr.psi.impl.SutrLiteralTypeImpl;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created by stryderc on 1/8/2016.
 */
class SutrGenerator {

    static StringBuilder buildIntent(List<SutrFile> sutrFiles) throws SutrGeneratorException {
        Intents intents = new Intents();
        List<Intent> intentCollection = new ArrayList<>();

        BuildSutrDefinitions sutrDefinitions = new BuildSutrDefinitions(sutrFiles).invoke();

        if (sutrDefinitions.getSutrObjectList().isEmpty()) {
            throw new SutrGeneratorException("No Sutr definitions found");
        }

        for (final SutrObject sutrDef : sutrDefinitions.getSutrObjectList()) {

            String intent_name = sutrDef.getSutrName().getText();

            List<Slot> slots = new ArrayList<>();

            for (SutrParam param : sutrDef.getSutrParams().getSutrParamList()) {
                String typeName = param.getTypeName().getText();
                String paramName = param.getParamName().getText();

                String type;

                if (sutrDefinitions.sutrLiteralTypeKeys.containsKey(typeName)) {
                    type = ActionUtil.BUILT_IN_TYPES.get("literal");
                } else if (sutrDefinitions.sutrCustomTypeKeys.containsKey(typeName)){
                    type = typeName;
                } else if (ActionUtil.BUILT_IN_TYPES.containsKey(typeName)){
                    type = ActionUtil.BUILT_IN_TYPES.get(typeName);
                } else{
                    throw new SutrGeneratorException("No valid type found for slot [" + typeName + " " + paramName + "] in Sutr ["+sutrDef.getContainingFile().getName()+": " + sutrDef.getSutrName().getText()+"]");
                }

                slots.add(new Slot(paramName, type));
            }

            intentCollection.add(new Intent(intent_name, slots));
        }


        Gson gson = new Gson();
        intents.intents = intentCollection;

        return new StringBuilder(gson.toJson(intents));
    }

    @NotNull
    static StringBuilder buildUtterances(List<SutrFile> sutrFiles) throws SutrGeneratorException {


        BuildSutrDefinitions sutrDefinitions = new BuildSutrDefinitions(sutrFiles).invoke();

        if (sutrDefinitions.getSutrObjectList().isEmpty()) {
            throw new SutrGeneratorException("No Sutr definitions found");
        }

        List<Utterance> utterances = new ArrayList<>();
        final LiteralMapper literalsMap = new LiteralMapper(sutrDefinitions.getSutrLiteralTypeKeys());

        for (final SutrObject sutrObject : sutrDefinitions.getSutrObjectList()) {

            String utteranceName = sutrObject.getSutrName().getText();

            final SutrBody sutrBody = sutrObject.getSutrBody();

            if (sutrBody != null) {
                for (SutrUtterance utterance : sutrBody.getUtteranceList()) {

                    List<String> utteranceList = literalsMap.Map(utterance, sutrObject);

                    for (String utteranceText : utteranceList) {
                        utterances.add(new Utterance(utteranceName, utteranceText));
                    }
                }
            }
        }

        StringBuilder builder = new StringBuilder();

        for (Utterance utterance : utterances) {
            builder.append(utterance.name).append(" ").append(utterance.body).append("\n");
        }
        return builder;
    }

    private static Map<String, List<String>> getLiterals(final SutrFile sutrFile) {
        Map<String, List<String>> literalsMap = new HashMap<>();

        for (final SutrLiteralTypeImpl sutrLiteralType : sutrFile.findChildrenByClass(SutrLiteralTypeImpl.class)) {
            List<String> literalPhrases = new ArrayList<>();
            for (SutrLiteralPhrase sutrLiteralPhrase : sutrLiteralType.getLiteralPhrases().getLiteralPhraseList()) {
                literalPhrases.add(sutrLiteralPhrase.getText());
            }
            literalsMap.put(sutrLiteralType.getTypeName().getText(), literalPhrases);
        }

        return literalsMap;
    }

    static StringBuilder buildNodeLauncher(List<SutrFile> sutrFiles) throws SutrGeneratorException {

        String premable =
                "function onIntent(intentRequest, session, callback) {\n" +
                        "    console.log(\"onIntent requestId=\" + intentRequest.requestId +\n" +
                        "        \", sessionId=\" + session.sessionId);\n" +
                        "\n" +
                        "    var intent = intentRequest.intent,\n" +
                        "        intentName = intentRequest.intent.name;\n" +
                        "\n";

        StringBuilder builder = new StringBuilder(premable);
        List<SutrObject> sutrCollection = getSutrObjects(sutrFiles);

        int count = 0;

        for (SutrObject sutr : sutrCollection) {
            if (count > 0) {
                builder.append(" else ");
            } else {
                count++;
                builder.append("    ");
            }

            final SutrFunctionPointer functionPointer = sutr.getFunctionPointer();

            if (functionPointer == null) {
                throw new SutrGeneratorException("No function pointer defined for " + sutr.getSutrName().getText());
            }

            String stmt =
                    "if (\"" + sutr.getSutrName().getText() + "\" === intentName) {\n" +
                            "        " + functionPointer.getFunctionName().getText() + "(intent, session, callback);\n" +
                            "    }";

            builder.append(stmt);
        }

        String postamble =
                " else {\n" +
                        "        throw \"Invalid intent\";\n" +
                        "     }\n" +
                        "}\n";

        builder.append(postamble);

        return builder;
    }

    private static List<SutrObject> getSutrObjects(List<SutrFile> sutrFiles) {
        List<SutrObject> sutrObjects = new ArrayList<>();

        for (SutrFile file : sutrFiles) {
            final SutrObject[] sutrDefinitions = file.findChildrenByClass(SutrObject.class);
            Collections.addAll(sutrObjects, sutrDefinitions);
        }

        return sutrObjects;
    }

    static StringBuilder buildPythonLauncher(List<SutrFile> sutrFiles) throws SutrGeneratorException {

        StringBuilder builder = new StringBuilder(PythonText.GeneratorPreamble);

        List<SutrObject> sutrCollection = getSutrObjects(sutrFiles);

        builder.append("\n").append(PythonText.OnIntentPreamble);
        int count = 0;

        for (SutrObject sutr : sutrCollection) {
            final SutrFunctionPointer functionPointer = sutr.getFunctionPointer();

            if (functionPointer == null) {
                throw new SutrGeneratorException("No function pointer defined for " + sutr.getSutrName().getText());
            }

            if (count == 0) {
                builder.append("\n    if ");
                count++;
            } else {
                builder.append("\n    elif ");
            }

            builder.append("intent_name == \"")
                    .append(sutr.getSutrName().getText())
                    .append("\":\n");

            StringBuilder paramBuilder = new StringBuilder("intent, session");
            StringBuilder defaultParamValueBuilder = new StringBuilder();

            for (final SutrParam sutrSutrParam : sutr.getSutrParams().getSutrParamList()) {
                final String paramName = sutrSutrParam.getParamName().getText();

                final String defaultValue = sutrSutrParam.getDefaultValue();
                defaultParamValueBuilder
                        .append("        ")
                        .append(paramName.toLowerCase())
                        .append(" = ")
                        .append("intent['slots'].get('")
                        .append(paramName)
                        .append("', '")
                        .append(defaultValue)
                        .append("')\n");

                paramBuilder.append(", ")
                        .append(paramName.toLowerCase());
            }

            builder.append(defaultParamValueBuilder.toString());

            builder.append("        return ")
                    .append(functionPointer.getFunctionName().getText())
                    .append("(")
                    .append(paramBuilder.toString())
                    .append(")");
        }


        return builder.append("\n");
    }

    static StringBuilder buildCustomTypeItems(final SutrCustomType customType) throws SutrGeneratorException {
        StringBuilder customTypeItemListBuilder = new StringBuilder();

        final List<SutrCustomTypeItem> customTypeItemList = customType.getCustomTypeItems().getCustomTypeItemList();
        if (customTypeItemList.isEmpty()) {
            throw new SutrGeneratorException("No items found in list");
        }

        for (final SutrCustomTypeItem sutrCustomTypeItem : customTypeItemList) {
            customTypeItemListBuilder.append(sutrCustomTypeItem.getText()).append("\n");
        }

        return customTypeItemListBuilder;
    }

    static StringBuilder buildHandler(List<SutrFile> sutrFiles, String language) throws SutrGeneratorException {
        if(language.equals("Javascript")){
            return buildNodeLauncher(sutrFiles);
        }
        return buildPythonLauncher(sutrFiles);

    }

    private static class BuildSutrDefinitions {
        private final List<SutrFile> sutrFiles;
        private List<SutrObject> sutrObjectList;
        private Map<String, SutrCustomType> sutrCustomTypeKeys;
        private Map<String, SutrLiteralType> sutrLiteralTypeKeys;

        BuildSutrDefinitions(final List<SutrFile> sutrFiles) {
            this.sutrFiles = sutrFiles;
        }

        List<SutrObject> getSutrObjectList() {
            return sutrObjectList;
        }

        public Map<String, SutrCustomType> getSutrCustomTypeKeys() {
            return sutrCustomTypeKeys;
        }

        Map<String, SutrLiteralType> getSutrLiteralTypeKeys() {
            return sutrLiteralTypeKeys;
        }

        BuildSutrDefinitions invoke() throws SutrGeneratorException {
            sutrObjectList = new ArrayList<>();
            sutrCustomTypeKeys = new HashMap<>();
            sutrLiteralTypeKeys = new HashMap<>();
            Map<String, SutrObject> tempSutr = new HashMap<>();

            for (SutrFile file : sutrFiles) {

                final SutrObject[] sutrObjects = file.findChildrenByClass(SutrObject.class);
                //We need to look at any import statements and pull declared types in from that file.
                SutrImportStmt[] importStmts = file.findChildrenByClass(SutrImportStmt.class);
                for (SutrImportStmt importStmt : importStmts) {

                    SutrTypeDefinitionReference typeReference = importStmt.resolveReference();
                    if(typeReference == null){
                        throw new SutrGeneratorException("Unable to resolve import statement");
                    } else if(typeReference instanceof SutrCustomType){
                        sutrCustomTypeKeys.put(typeReference.getTypeName().getText(), (SutrCustomType) typeReference);
                    }
                    else if(typeReference instanceof SutrLiteralType){
                        sutrLiteralTypeKeys.put(typeReference.getTypeName().getText(), (SutrLiteralType) typeReference);
                    }
                }

                for (final SutrObject sutrObject : sutrObjects) {

                    final String sutrName = sutrObject.getSutrName().getText();

                    if(tempSutr.containsKey(sutrName)) {
                        String message =
                                "Sutr '" + sutrName + "' defined more than once in files [" +
                                        tempSutr.get(sutrName).getContainingFile().getName() + ", " +
                                        sutrObject.getContainingFile().getName() + "]";

                        throw new SutrGeneratorException(message);
                    } else {
                        tempSutr.put(sutrName, sutrObject);
                    }

                    sutrObjectList.add(sutrObject);
                }


                for (final SutrCustomType customType : file.findChildrenByClass(SutrCustomType.class)) {
                    final String typeName = customType.getTypeName().getText();

                    if (sutrCustomTypeKeys.containsKey(typeName)) {
                        String message =
                                "Custom Type '" + typeName + "' defined more than once in files [" +
                                sutrCustomTypeKeys.get(typeName).getContainingFile().getName() +", " +
                                customType.getContainingFile().getName() + "]";

                        throw new SutrGeneratorException(message);
                    }

                    sutrCustomTypeKeys.put(typeName, customType);
                }

                for (final SutrLiteralType literalType : file.findChildrenByClass(SutrLiteralType.class)) {
                    final String typeName = literalType.getTypeName().getText();

                    if (sutrLiteralTypeKeys.containsKey(typeName)) {

                        String message =
                                "Literal Type '" + typeName + "' defined more than once in files [" +
                                sutrLiteralTypeKeys.get(typeName).getContainingFile().getName() +", " +
                                literalType.getContainingFile().getName() + "]";

                        throw new SutrGeneratorException(message);
                    }

                    sutrLiteralTypeKeys.put(typeName, literalType);
                }
            }
            return this;
        }
    }
}
