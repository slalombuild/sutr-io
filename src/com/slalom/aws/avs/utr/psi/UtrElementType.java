package com.slalom.aws.avs.utr.psi;

import com.intellij.psi.tree.IElementType;
import com.slalom.aws.avs.utr.UtrLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Created by stryderc on 10/21/2015.
 */
public class UtrElementType extends IElementType {
    public UtrElementType(@NotNull @NonNls String debugName){
        super(debugName, UtrLanguage.INSTANCE);
    }


}
