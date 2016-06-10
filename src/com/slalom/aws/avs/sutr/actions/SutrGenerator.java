package com.slalom.aws.avs.sutr.actions;

import com.google.gson.Gson;
import com.slalom.aws.avs.sutr.SutrPluginUtil;
import com.slalom.aws.avs.sutr.actions.constants.PythonText;
import com.slalom.aws.avs.sutr.actions.exceptions.SutrGeneratorException;
import com.slalom.aws.avs.sutr.conf.SutrConfigProvider;
import com.slalom.aws.avs.sutr.models.Intents;
import com.slalom.aws.avs.sutr.models.Utterance;
import com.slalom.aws.avs.sutr.mustache.SutrMustacheBuilderException;
import com.slalom.aws.avs.sutr.mustache.SutrMustacheModelBuilder;
import com.slalom.aws.avs.sutr.mustache.models.SutrIntentModel;
import com.slalom.aws.avs.sutr.mustache.models.SutrSlotModel;
import com.slalom.aws.avs.sutr.psi.*;
import com.slalom.aws.avs.sutr.psi.impl.SutrLiteralTypeImpl;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created by stryderc on 1/8/2016.
 */
public class SutrGenerator {

    static StringBuilder buildIntent(List<SutrFile> sutrFiles) throws SutrGeneratorException {
        Intents intents = new Intents();

        BuildSutrDefinitions sutrDefinitions = new BuildSutrDefinitions(sutrFiles).invoke();

        if (sutrDefinitions.getSutrObjectList().isEmpty()) {
            throw new SutrGeneratorException("No Sutr definitions found");
        }

        List<SutrIntentModel> sutrIntentModelCollection = GetModels(sutrDefinitions);

        Gson gson = new Gson();
        intents.sutrIntentModels = sutrIntentModelCollection;

        return new StringBuilder(gson.toJson(intents));
    }

    public static List<SutrIntentModel> GetModels(BuildSutrDefinitions sutrDefinitions) throws SutrGeneratorException {
        List<SutrIntentModel> sutrIntentModelCollection = new ArrayList<>();

        List<SutrObject> sutrObjectList = sutrDefinitions.getSutrObjectList();
        for (int i1 = 0; i1 < sutrObjectList.size(); i1++) {
            SutrObject sutrDef = sutrObjectList.get(i1);

            String intent_name = sutrDef.getSutrName().getText();

            List<SutrSlotModel> slots = new ArrayList<>();

            List<SutrParam> sutrParamList = sutrDef.getSutrParams().getSutrParamList();
            for (int i = 0; i < sutrParamList.size(); i++) {
                SutrParam param = sutrParamList.get(i);
                String typeName = param.getTypeName().getText();
                String paramName = param.getParamName().getText();

                String type;

                if (sutrDefinitions.sutrLiteralTypeKeys.containsKey(typeName)) {
                    type = ActionUtil.BUILT_IN_TYPES.get("literal");
                } else if (sutrDefinitions.sutrCustomTypeKeys.containsKey(typeName)) {
                    type = typeName;
                } else if (ActionUtil.BUILT_IN_TYPES.containsKey(typeName)) {
                    type = ActionUtil.BUILT_IN_TYPES.get(typeName);
                } else {
                    throw new SutrGeneratorException("No valid slotType found for slot [" + typeName + " " + paramName + "] in Sutr [" + sutrDef.getContainingFile().getName() + ": " + sutrDef.getSutrName().getText() + "]");
                }

                SutrSlotModel slot = new SutrSlotModel();
                slot.slotName = paramName;
                slot.slotType = type;
                slot.first = i < 1;
                slot.defaultValue = param.getDefaultValue();

                slots.add(slot);
            }

            SutrIntentModel e = new SutrIntentModel(intent_name, slots);
            e.first = i1<1;
            e.functionName = sutrDef.getFunctionPointer().getFunctionName().getText();
            sutrIntentModelCollection.add(e);
        }

        return sutrIntentModelCollection;
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
                "functionName onIntent(intentRequest, session, callback) {\n" +
                        "    console.log(\"onIntent requestId=\" + intentRequest.requestId +\n" +
                        "        \", sessionId=\" + session.sessionId);\n" +
                        "\n" +
                        "    var intentName = intentRequest.intentName,\n" +
                        "        intentName = intentRequest.intentName.slotName;\n" +
                        "\n";

        StringBuilder builder = new StringBuilder(premable);
        List<SutrObject> sutrCollection = getSutrObjectModel(sutrFiles);

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
                throw new SutrGeneratorException("No functionName pointer defined for " + sutr.getSutrName().getText());
            }

            String stmt =
                    "if (\"" + sutr.getSutrName().getText() + "\" === intentName) {\n" +
                            "        " + functionPointer.getFunctionName().getText() + "(intentName, session, callback);\n" +
                            "    }";

            builder.append(stmt);
        }

        String postamble =
                " else {\n" +
                        "        throw \"Invalid intentName\";\n" +
                        "     }\n" +
                        "}\n";

        builder.append(postamble);

        return builder;
    }

    public static List<SutrObject> getSutrObjectModel(List<SutrFile> sutrFiles) {
        List<SutrObject> sutrObjects = new ArrayList<>();

        for (SutrFile file : sutrFiles) {
            final SutrObject[] sutrDefinitions = file.findChildrenByClass(SutrObject.class);
            Collections.addAll(sutrObjects, sutrDefinitions);
        }

        return sutrObjects;
    }

    static StringBuilder buildPythonLauncher(List<SutrFile> sutrFiles) throws SutrGeneratorException {

        StringBuilder builder = new StringBuilder(PythonText.GeneratorPreamble);

        List<SutrObject> sutrCollection = getSutrObjectModel(sutrFiles);

        builder.append("\n").append(PythonText.OnIntentPreamble);

        int count = 0;

        for (SutrObject sutr : sutrCollection) {
            final SutrFunctionPointer functionPointer = sutr.getFunctionPointer();

            if (functionPointer == null) {
                throw new SutrGeneratorException("No functionName pointer defined for " + sutr.getSutrName().getText());
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

            StringBuilder paramBuilder = new StringBuilder("intentName, session");
            StringBuilder defaultParamValueBuilder = new StringBuilder();

            for (final SutrParam sutrSutrParam : sutr.getSutrParams().getSutrParamList()) {
                final String paramName = sutrSutrParam.getParamName().getText();

                final String defaultValue = sutrSutrParam.getDefaultValue();
                defaultParamValueBuilder
                        .append("        ")
                        .append(paramName.toLowerCase())
                        .append(" = ")
                        .append("intentName['slots'].get('")
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
        SutrConfigProvider config = SutrPluginUtil.getConfigProvider();

//            String handlerTemplateLocation = config.handlerTemplateLocation;

//            String template = config.getCurrentHandlerTemplatePath();

        String template =  "C:\\Users\\stryderc\\dev\\sources\\sutr-io\\src\\resources\\templates\\python.mustache";

        SutrMustacheModelBuilder modelBuilder = new SutrMustacheModelBuilder(template);
        try {
            modelBuilder.Build(sutrFiles);
            return new StringBuilder(modelBuilder.Compile());
        } catch (SutrMustacheBuilderException e) {
            e.printStackTrace();
        }

        return new StringBuilder();
    }

    public static class BuildSutrDefinitions {
        private final List<SutrFile> sutrFiles;
        private List<SutrObject> sutrObjectList;
        public Map<String, SutrCustomType> sutrCustomTypeKeys;
        public Map<String, SutrLiteralType> sutrLiteralTypeKeys;

        public BuildSutrDefinitions(final List<SutrFile> sutrFiles) {
            this.sutrFiles = sutrFiles;
        }

        public List<SutrObject> getSutrObjectList() {
            return sutrObjectList;
        }

        public Map<String, SutrCustomType> getSutrCustomTypeKeys() {
            return sutrCustomTypeKeys;
        }

        Map<String, SutrLiteralType> getSutrLiteralTypeKeys() {
            return sutrLiteralTypeKeys;
        }

        public BuildSutrDefinitions invoke() throws SutrGeneratorException {
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
