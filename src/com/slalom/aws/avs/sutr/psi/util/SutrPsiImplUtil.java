package com.slalom.aws.avs.sutr.psi.util;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.slalom.aws.avs.sutr.SutrLanguageType;
import com.slalom.aws.avs.sutr.psi.*;

/**
 * Created by Stryder on 1/17/2016.
 */
public class SutrPsiImplUtil {

    public static PsiElement getNameIdentifier(SutrSutrParam element) {
        ASTNode keyNode = element.getNode().findChildByType(SutrTypes.SutrPARAM_NAME);
        if (keyNode != null) {
            return keyNode.getPsi();
        } else {
            return null;
        }
    }

    public static SutrTypeDefinitionReference[] getTypeReferences(SutrImportStmt importStmt){
        String fileName = importStmt.getFileName().getText() + "." + SutrLanguageType.INSTANCE.getDefaultExtension();
        final PsiDirectory containingDirectory = importStmt.getContainingFile().getContainingDirectory();


        if (containingDirectory != null) {
            PsiFile importFile = containingDirectory.findFile(fileName);
            if (importFile != null) {
                return ((SutrFile) importFile).findChildrenByClass(SutrTypeDefinitionReference.class);
            }
        }

        return null;
    }

    public static SutrTypeDefinitionReference resolveReference(SutrImportStmt importStmt){
        String fileName = importStmt.getFileName().getText() + "." + SutrLanguageType.INSTANCE.getDefaultExtension();

        final PsiDirectory containingDirectory = importStmt.getContainingFile().getContainingDirectory();

        if (containingDirectory != null) {
            PsiFile importFile = containingDirectory.findFile(fileName);
            if (importFile != null) {
                for (SutrTypeDefinitionReference sutrTypeDefinitionReference : ((SutrFile) importFile).findChildrenByClass(SutrTypeDefinitionReference.class)) {
                    if(sutrTypeDefinitionReference.getTypeName().getText().equals(importStmt.getTypeName().getText())){
                        return sutrTypeDefinitionReference;
                    }
                }
            }
        }

        return null;
    }


    public static SutrSutrObject getParentSutr(SutrParamName paramName) {
        return (SutrSutrObject)paramName.getParent().getParent().getParent();
    }

    public static SutrSutrObject getParentSutr(SutrSlotName slotName) {
        return (SutrSutrObject)slotName.getParent().getParent().getParent();
    }

    public static SutrSutrObject getParentSutr(SutrSutrParam slotName) {
        return (SutrSutrObject)slotName.getParent().getParent().getParent();
    }

    public static SutrSutrObject getParentSutr(SutrTypeName slotName) {
        return (SutrSutrObject)slotName.getParent().getParent().getParent();
    }

    public static SutrSutrObject getParentSutr(SutrSlot slot) {
        return (SutrSutrObject)slot.getParent().getParent().getParent();
    }

    public static String getDefaultValue(SutrSutrParam param){
        return "";
    }
}
