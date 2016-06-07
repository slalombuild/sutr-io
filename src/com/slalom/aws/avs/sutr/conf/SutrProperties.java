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

    @PropertyName(value = "sutr.handlerOutputLocation")
    String handlerOutputLocation;
    String defaultHandlerOutputLocation = "$PROJECT_ROOT$/ask/";

    @PropertyName(value = "sutr.intentOutputLocation")
    String intentOutputLocation;
    String defaultIntentOutputLocation = "$PROJECT_ROOT$/ask/intent.json";

    @PropertyName(value = "sutr.utterancesOutputLocation")
    String utterancesOutputLocation;
    String defaultUtterancesOutputLocation = "$PROJECT_ROOT$/ask/skill.utr";

    @PropertyName(value="sutr.useCustomPaths", defaultValue = "false")
    boolean useCustomPaths;

}
