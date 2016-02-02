package com.slalom.aws.avs.sutr.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import com.slalom.aws.avs.sutr.psi.*;
import com.slalom.aws.avs.sutr.psi.impl.SutrCustomTypeImpl;

import java.util.*;

/**
 * Created by Stryder on 1/17/2016.
 */
public class ActionUtil {

    protected static String EvaluateType(final SutrSutrParam param) {
        String paramType = param.getTypeName().getText();
        final String lowerCaseType = paramType.toLowerCase();

        if(BUILT_IN_TYPES.containsKey(lowerCaseType)){
            paramType = BUILT_IN_TYPES.get(lowerCaseType);
        }
        else if (ActionUtil.isLiteral(param.getTypeName())) {
            paramType = "AMAZON.LITERAL";
        }

        return paramType;
    }

    protected static boolean isLiteral(final SutrTypeName param) {
        final SutrLiteralType[] literalTypesInFile = ((SutrFile) param.getContainingFile()).findChildrenByClass(SutrLiteralType.class);
        for (SutrLiteralType type: literalTypesInFile) {
            if (type.getTypeName().getText().equals(param.getText())) {
                return true;
            }
        }
        return false;
    }

    public static final Map<String, String> BUILT_IN_TYPES;

    static {
        BUILT_IN_TYPES = new HashMap<String, String>();
        BUILT_IN_TYPES.put("date", "AMAZON.DATE");
        BUILT_IN_TYPES.put("duration", "AMAZON.DURATION");
        BUILT_IN_TYPES.put("digits", "AMAZON.FOUR_DIGIT");
        BUILT_IN_TYPES.put("number", "AMAZON.NUMBER");
        BUILT_IN_TYPES.put("time", "AMAZON.TIME");
        BUILT_IN_TYPES.put("city", "AMAZON.US_CITY");
        BUILT_IN_TYPES.put("name", "AMAZON.US_FIRST_NAME");
        BUILT_IN_TYPES.put("state", "AMAZON.US_STATE");
        BUILT_IN_TYPES.put("literal", "AMAZON.LITERAL");
    }

    public static List<String> getLiteralPhrases(final SutrTypeName typeName) {
        List<String> phrases = new ArrayList<>();

        final SutrLiteralType[] literalTypesInFile = ((SutrFile) typeName.getContainingFile()).findChildrenByClass(SutrLiteralType.class);
        for (SutrLiteralType type: literalTypesInFile) {
            if (type.getTypeName().getText().equals(typeName.getText())) {
                final SutrLiteralPhrases literalPhrases = type.getLiteralPhrases();
                for (final SutrLiteralPhrase sutrLiteralPhrase : literalPhrases.getLiteralPhraseList()) {
                    phrases.add(sutrLiteralPhrase.getText());
                }
            }
        }

        return phrases;
    }

    public static List<SutrFile> getFiles(final AnActionEvent e) {
        Project project = getEventProject(e);
        VirtualFile[] files = LangDataKeys.VIRTUAL_FILE_ARRAY.getData(e.getDataContext());
        if(project == null || files == null) return Collections.emptyList();
        final PsiManager manager = PsiManager.getInstance(project);
        return ContainerUtil.mapNotNull(files, new Function<VirtualFile, SutrFile>() {
            @Override
            public SutrFile fun(final VirtualFile virtualFile) {
                PsiFile psiFile = manager.findFile(virtualFile);
                return psiFile instanceof SutrFile ? (SutrFile)psiFile : null;
            }
        });
    }

    private static Project getEventProject(final AnActionEvent e) {
        return e == null ? null : e.getData(LangDataKeys.PROJECT);
    }

    public static void ShowErrorMessage(final String message, AnActionEvent e) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(LangDataKeys.PROJECT.getData(e.getDataContext()));

        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(message, MessageType.ERROR, null)
                .setFadeoutTime(5000)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()), Balloon.Position.atRight);
    }

    public static void ShowInfoMessage(final String message, final AnActionEvent e) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(LangDataKeys.PROJECT.getData(e.getDataContext()));

        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(message, MessageType.INFO, null)
                .setFadeoutTime(5000)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()), Balloon.Position.atRight);
    }

    public static SutrCustomType getCustomTypeUnderCaret(AnActionEvent e){
        final PsiFile file = e.getData(LangDataKeys.PSI_FILE);
        final Editor editor = e.getData(LangDataKeys.EDITOR);
        if(file == null || editor == null){
            return null;
        }

        PsiElement psiElement = file.findElementAt(editor.getCaretModel().getOffset());

        while(psiElement != null && !(psiElement instanceof SutrFile)){
            if(psiElement instanceof SutrCustomTypeImpl){
                return (SutrCustomType) psiElement;
            }
            psiElement = psiElement.getParent();
        }

        return null;
    }
}
