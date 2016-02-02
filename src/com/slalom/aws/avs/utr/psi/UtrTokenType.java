package com.slalom.aws.avs.utr.psi;

import com.intellij.psi.tree.IElementType;
import com.slalom.aws.avs.utr.UtrLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class UtrTokenType extends IElementType {
    public UtrTokenType(@NotNull @NonNls String debugName){
        super(debugName, UtrLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "UtrTokenType." + super.toString();
    }
}
