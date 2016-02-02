package com.slalom.aws.avs.sutr;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Stryder on 1/23/2016.
 */

public class SutrCompletionContributor extends CompletionContributor {
    public SutrCompletionContributor() {

//        final CompletionProvider<CompletionParameters> provider = new CompletionProvider<CompletionParameters>() {
//            @Override
//            protected void addCompletions(@NotNull final CompletionParameters parameters, final ProcessingContext context, @NotNull final CompletionResultSet result) {
//
//                final LookupElement element = new LookupElement() {
//                    @NotNull
//                    @Override
//                    public String getLookupString() {
//                        return "foobard";
//                    }
//                };
//                result.addElement(element);
//            }
//        };
//
//        extend(CompletionType.BASIC, psiElement().withElementType(SutrTypes.SutrTYPE_NAME), provider);
//        extend(CompletionType.BASIC, psiElement().withElementType(SutrTypes.SutrNAME), provider);

//        extend(CompletionType.BASIC, psiElement().withElementType(SutrTypes.SutrLS), provider);
//        extend(CompletionType.BASIC, psiElement().withElementType(SutrTypes.SutrLP), provider);
//        extend(CompletionType.BASIC, psiElement().withElementType(SutrTypes.SutrLB), provider);
//        extend(CompletionType.BASIC, psiElement().withElementType(SutrTypes.SutrSUTR_PARAMS), provider);
//        extend(CompletionType.BASIC, psiElement().withElementType(SutrTypes.SutrSUTR_PARAM), provider);
//        extend(CompletionType.BASIC, psiElement().withElementType(SutrTypes.SutrPARAM_NAME), provider);
    }

    @Override
    public boolean invokeAutoPopup(@NotNull final PsiElement position, final char typeChar) {
        return true;
    }

    @Override
    public void fillCompletionVariants(@NotNull final CompletionParameters parameters, @NotNull final CompletionResultSet result) {
        super.fillCompletionVariants(parameters, result);
    }
}
