package com.slalom.aws.avs.sutr.psi;

import com.intellij.psi.tree.IElementType;
import com.slalom.aws.avs.sutr.SutrLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class SutrTokenType extends IElementType {
    public SutrTokenType(@NotNull @NonNls String debugName){
        super(debugName, SutrLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "SutrTokenType." + super.toString();
    }
}
