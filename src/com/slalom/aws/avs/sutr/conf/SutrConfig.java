package com.slalom.aws.avs.sutr.conf;

import com.intellij.ide.util.PropertyName;

import java.util.List;

/**
 * Created by stryderc on 6/7/2016.
 *
 * Properties that are used by the Sutr.io plugin.
 */
public class SutrConfig {

    @PropertyName(value="sutr.templates")
    public List<String> templateLocations;

    @PropertyName(value = "sutr.handlerTemplateFileLocation")
    public String handlerTemplateLocation;

    @PropertyName(value = "sutr.handlerOutputLocation")
    public String handlerOutputLocation;

    @PropertyName(value = "sutr.intentOutputLocation")
    public String intentOutputLocation;

    @PropertyName(value = "sutr.utterancesOutputLocation")
    public String utterancesOutputLocation;

    @PropertyName(value="sutr.useCustomPaths", defaultValue = "false")
    public boolean useCustomPaths;

    @PropertyName(value="sutr.selecteHandlerTemplate")
    public String selecteHandlerTemplate;
    private String handlerTemplate;


}
