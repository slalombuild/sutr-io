package com.slalom.aws.avs.sutr.conf;

import com.intellij.ide.util.PropertyName;

/**
 * Created by stryderc on 6/7/2016.
 *
 * Properties that are used by the Sutr.io plugin.
 */
class SutrProperties {

    @PropertyName(value = "sutr.handlerTemplateFileLocation")
    String handlerTemplateLocation;

    @PropertyName(value = "sutr.intentOutputLocation")
    String intentOutputLocation;

    @PropertyName(value = "sutr.utterancesOutputLocation")
    String utterancesOutputLocation;

    @PropertyName(value = "sutr.handlerOutputLocation")
    String handlerOutputLocation;

    @PropertyName(value="sutr.useCustomPaths", defaultValue = "false")
    boolean useCustomPaths;
}
