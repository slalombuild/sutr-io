package com.slalom.aws.avs.sutr.conf;

import com.intellij.ide.util.PropertyName;

import java.util.List;

/**
 * Created by stryderc on 6/7/2016.
 *
 * Properties that are used by the Sutr.io plugin.
 */
public class SutrConfig {

    @PropertyName(value="sutr.useCustomPaths", defaultValue = "false")
    public boolean useCustomPaths;

    @PropertyName(value="sutr.templates")
    public List<String> templatePaths;

    @PropertyName(value = "sutr.handlerTemplateFilePath")
    public String handlerTemplatePath;

    @PropertyName(value = "sutr.handlerOutputPath")
    public String handlerOutputPath;

    @PropertyName(value = "sutr.intentOutputPath")
    public String intentOutputPath;

    @PropertyName(value = "sutr.utterancesOutputPath")
    public String utterancesOutputPath;

    @PropertyName(value = "sutr.customTypesOutputPath")
    public String customTypesOutputPath;

    @PropertyName(value="sutr.selectedHandlerTemplate")
    public String selectedHandlerTemplate;

}
