package com.slalom.aws.avs.utr;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.slalom.aws.avs.utr.parser.UtrPsiCompositeElement;
import com.slalom.aws.avs.utr.psi.UtrTypes;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Stryder on 1/17/2016.
 */
public class UtrAnnotator implements Annotator {
    private static final Logger log = Logger.getInstance(UtrAnnotator.class);

    @Override
    public void annotate(@NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {

        log.info("Annotating Utr element [" + psiElement + "]");

        if(!(psiElement.getContext() instanceof UtrPsiCompositeElement)) return;

        final UtrPsiCompositeElement psiElementContext = (UtrPsiCompositeElement) psiElement.getContext();
        final IElementType tokenType = psiElementContext.getTokenType();


        if(tokenType.equals(UtrTypes.UtrSLOT)){
            annotationHolder.createInfoAnnotation(psiElementContext , null).setTextAttributes(UtrSyntaxHighlighter.SLOT);
        } else if(tokenType.equals(UtrTypes.UtrPHRASE)){
            annotationHolder.createInfoAnnotation(psiElement, null).setTextAttributes(UtrSyntaxHighlighter.LITERAL_PHRASE);
        }

////        if(tokenType == )
//        annotationHolder.createInfoAnnotation()
//        final UtrElementType sutrElement= (UtrElementType) psiElement;
////        if (getElementType() == UtrTypes.UtrTYPE


    }
}
