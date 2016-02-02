package com.slalom.aws.avs.sutr.psi;

import com.intellij.psi.tree.IElementType;
import com.slalom.aws.avs.sutr.SutrLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Created by stryderc on 10/21/2015.
 */
public class SutrElementType extends IElementType {
    public SutrElementType(@NotNull @NonNls String debugName){
        super(debugName, SutrLanguage.INSTANCE);
    }


}
