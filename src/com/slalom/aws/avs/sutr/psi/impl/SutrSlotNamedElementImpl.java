package com.slalom.aws.avs.sutr.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.util.IncorrectOperationException;
import com.slalom.aws.avs.sutr.SutrElementFactory;
import com.slalom.aws.avs.sutr.psi.SutrParamName;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Stryder on 1/22/2016.
 */
public class SutrSlotNamedElementImpl extends SutrCompositeElementImpl implements PsiNamedElement {
    public SutrSlotNamedElementImpl(@NotNull final ASTNode astNode) {
        super(astNode);
    }

    @Override
    public PsiElement setName(@NonNls @NotNull final String name) throws IncorrectOperationException {
        SutrParamName newNode = SutrElementFactory.createParamNameElement(getProject(), name);
        this.getParent().getNode().replaceChild(getNode(), newNode.getNode());
        return this;
    }

    @Override
    public String getName() {
        return this.getText();
    }

    @NotNull
    @Override
    public GlobalSearchScope getResolveScope() {
        return super.getResolveScope();
    }

    @NotNull
    @Override
    public SearchScope getUseScope() {

        return super.getUseScope();
    }

    @Override
    public PsiReference getReference() {

        return super.getReference();
    }


}
