package com.slalom.aws.avs.sutr.psi.impl;

import com.slalom.aws.avs.sutr.psi.SutrNamedElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public abstract class SutrNamedElementImpl extends ASTWrapperPsiElement implements SutrNamedElement {
    public SutrNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}