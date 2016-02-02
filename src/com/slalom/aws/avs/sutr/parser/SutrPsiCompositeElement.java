package com.slalom.aws.avs.sutr.parser;

import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.tree.IElementType;

/**
 * Created by Stryder on 1/17/2016.
 */
public interface SutrPsiCompositeElement extends NavigatablePsiElement {
    IElementType getTokenType();
}
