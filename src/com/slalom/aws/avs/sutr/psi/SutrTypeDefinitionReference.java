package com.slalom.aws.avs.sutr.psi;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Stryder on 1/22/2016.
 */
public interface SutrTypeDefinitionReference extends SutrReference {
    @NotNull
    SutrTypeName getTypeName();

}
