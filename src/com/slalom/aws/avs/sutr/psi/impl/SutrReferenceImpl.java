package com.slalom.aws.avs.sutr.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import com.slalom.aws.avs.sutr.psi.SutrReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Stryder on 1/22/2016.
 */
public class SutrReferenceImpl extends SutrCompositeElementImpl implements SutrReference {
    public SutrReferenceImpl(@NotNull final ASTNode astNode) {
        super(astNode);
    }

    @Override
    public PsiElement getElement() {
        return this;
    }

    @Override
    public TextRange getRangeInElement() {
        final TextRange textRange = getTextRange();
        return new TextRange(0, textRange.getEndOffset() - textRange.getStartOffset());
//        return getTextRange();
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        return null;
    }

    @Override
    public PsiReference getReference() {
        return null;
    }

    @NotNull
    @Override
    public String getCanonicalText() {
        return getText();
    }

    @Override
    public PsiElement handleElementRename(final String s) throws IncorrectOperationException {
        return null;
    }

    @Override
    public PsiElement bindToElement(@NotNull final PsiElement psiElement) throws IncorrectOperationException {
        return null;
    }

    @Override
    public boolean isReferenceTo(final PsiElement psiElement) {

        return false;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }

    @Override
    public boolean isSoft() {
        return false;
    }
}
