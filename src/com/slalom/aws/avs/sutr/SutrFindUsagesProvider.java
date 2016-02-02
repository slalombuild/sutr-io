package com.slalom.aws.avs.sutr;

import com.intellij.lang.HelpID;
import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.TokenSet;
import com.slalom.aws.avs.sutr.psi.SutrParamName;
import com.slalom.aws.avs.sutr.psi.SutrSlot;
import com.slalom.aws.avs.sutr.psi.SutrTypeName;
import com.slalom.aws.avs.sutr.psi.SutrTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Stryder on 1/22/2016.
 */
public class SutrFindUsagesProvider implements FindUsagesProvider {

    private DefaultWordsScanner _defaultWordsScanner;

    @Nullable
    @Override
    public WordsScanner getWordsScanner() {

        if (_defaultWordsScanner == null) {
            _defaultWordsScanner = new DefaultWordsScanner(new SutrLexerAdapter(), TokenSet.create(SutrTypes.SutrNAME), TokenSet.create(SutrTypes.SutrCOMMENT), TokenSet.EMPTY);
        }

        return _defaultWordsScanner;
    }

    @Override
    public boolean canFindUsagesFor(@NotNull final PsiElement psiElement) {
        if(psiElement instanceof SutrTypeName){
            return true;
        }

        if(psiElement instanceof SutrSlot){
            return true;
        }

        if(psiElement instanceof SutrParamName){
            return true;
        }



        return false;
    }

    @Nullable
    @Override
    public String getHelpId(@NotNull final PsiElement psiElement) {
        return HelpID.FIND_OTHER_USAGES;
    }

    @NotNull
    @Override
    public String getType(@NotNull final PsiElement psiElement) {
        if(psiElement instanceof SutrParamName){
            return "Sutr Param";
        }
        return "unknown";
    }

    @NotNull
    @Override
    public String getDescriptiveName(@NotNull final PsiElement psiElement) {
        if(psiElement instanceof SutrParamName){
            return psiElement.getParent().getText();
        }
        return psiElement.getText();
    }

    @NotNull
    @Override
    public String getNodeText(@NotNull final PsiElement psiElement, final boolean b) {
        final String descriptiveName = getDescriptiveName(psiElement);
        return descriptiveName;
    }
}
