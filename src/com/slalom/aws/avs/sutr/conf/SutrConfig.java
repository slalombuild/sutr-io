package com.slalom.aws.avs.sutr.conf;

import com.intellij.ide.util.PropertyName;

import java.util.List;

/**
 * Created by stryderc on 6/7/2016.
 *
 * Properties that are used by the Sutr.io plugin.
 */
public class SutrConfig {

    public SutrConfig() {
    }

    public SutrConfig(SutrConfig sutrConfig) {

        this.useCustomPaths = sutrConfig.useCustomPaths;
        this.templatePaths = sutrConfig.templatePaths;
        this.handlerTemplatePath = sutrConfig.handlerTemplatePath;
        this.selectedHandlerTemplate = sutrConfig.selectedHandlerTemplate;
        this.handlerOutputPath = sutrConfig.handlerOutputPath;
        this.intentOutputPath = sutrConfig.intentOutputPath;
        this.utterancesOutputPath = sutrConfig.utterancesOutputPath;
        this.customTypesOutputPath = sutrConfig.customTypesOutputPath;

        this.handlerCustomOutputPath = sutrConfig.handlerCustomOutputPath;
        this.intentCustomOutputPath = sutrConfig.intentCustomOutputPath;
        this.utterancesCustomOutputPath = sutrConfig.utterancesCustomOutputPath;
        this.customTypesCustomOutputPath = sutrConfig.customTypesCustomOutputPath;
    }

    @PropertyName(value="sutr.handlerCustomOutputPath")
    public String handlerCustomOutputPath;

    @PropertyName(value="sutr.intentCustomOutputPath")
    public String intentCustomOutputPath;

    @PropertyName(value="sutr.utterancesCustomOutputPath")
    public String utterancesCustomOutputPath;

    @PropertyName(value="sutr.customTypesCustomOutputPath")
    public String customTypesCustomOutputPath;

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
