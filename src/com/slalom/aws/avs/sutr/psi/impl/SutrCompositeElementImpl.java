package com.slalom.aws.avs.sutr.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.IElementType;
import com.slalom.aws.avs.sutr.parser.SutrPsiCompositeElement;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Stryder on 1/16/2016.
 */
public class SutrCompositeElementImpl extends ASTWrapperPsiElement implements SutrPsiCompositeElement {
    public SutrCompositeElementImpl(@NotNull final ASTNode astNode) {
        super(astNode);
    }

    @Override
    public IElementType getTokenType() {
        return getNode().getElementType();
    }
}
