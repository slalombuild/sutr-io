package com.slalom.aws.avs.sutr.conf;

import com.intellij.ide.util.PropertyName;

/**
 * Created by stryderc on 6/7/2016.
 *
 * Properties that are used by the Sutr.io plugin.
 */
public class SutrConfig {

    @PropertyName(value = "sutr.handlerTemplateFileLocation")
    public String handlerTemplateLocation;

    @PropertyName(value = "sutr.handlerOutputLocation")
    public String handlerOutputLocation;
    public String defaultHandlerOutputLocation = "$PROJECT_ROOT$/ask/";

    @PropertyName(value = "sutr.intentOutputLocation")
    public String intentOutputLocation;
    public String defaultIntentOutputLocation = "$PROJECT_ROOT$/ask/intent.json";

    @PropertyName(value = "sutr.utterancesOutputLocation")
    public String utterancesOutputLocation;
    public String defaultUtterancesOutputLocation = "$PROJECT_ROOT$/ask/skill.utr";

    @PropertyName(value="sutr.useCustomPaths", defaultValue = "false")
    public boolean useCustomPaths;

}
