package com.slalom.aws.avs.sutr.conf;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stryderc on 6/7/2016.
 *
 * Properties that are used by the Sutr.io plugin.
 */
public class SutrConfigProvider {

    public static final String PROJECT_ROOT_TOKEN = "$PROJECT_ROOT$";
    public static final String DEFAULT_HANDLER_OUTPUT_PATH = PROJECT_ROOT_TOKEN + "/ask/";
    private static final String DEFAULT_INTENT_OUTPUT_PATH = "$PROJECT_ROOT$/ask/intentName.json";
    private static final String DEFAULT_UTTERANCE_OUTPUT_PATH = "$PROJECT_ROOT$/ask/skill.utr";
    private static final String DEFAULT_CUSTOM_TYPES_OUTPUT_PATH = "$PROJECT_ROOT$/ask/custom.types";
    public static final String DEFAULT_PYTHON_TEMPLATE_PATH = "<Default Python>";
    public static final String DEFAULT_JAVASCRIPT_TEMPLATE_PATH = "<Default Javascript>";

    private Project _project;
    private SutrConfig config;
    private SutrConfig _configCopy;

    public void loadProperties(Project project) {
        assert project != null;

        _project = project;

        config = new SutrConfig();

        PropertiesComponent comp = PropertiesComponent.getInstance(_project);
        comp.loadFields(config);
        _configCopy = new SutrConfig(config);
    }

    public void apply() {

        PropertiesComponent comp = PropertiesComponent.getInstance(_project);

        comp.saveFields(config);

    }

    public void reset(){
        config = new SutrConfig(_configCopy);
    }

    public void useCustomPaths(boolean useCustomPaths) {
        config.useCustomPaths = useCustomPaths;
    }

    public boolean useCustomPaths() {
        return config.useCustomPaths;
    }

    public List<String> getHandlerTemplateLocations(){

        List<String> locations = new ArrayList<>();
        locations.add(DEFAULT_PYTHON_TEMPLATE_PATH);
        locations.add(DEFAULT_JAVASCRIPT_TEMPLATE_PATH);

        if(config.templatePaths != null){
            locations.addAll(config.templatePaths);
        }

        return locations;
    }

    public String getCurrentHandlerTemplatePath() {
        if(config.selectedHandlerTemplate == null || config.selectedHandlerTemplate.equals("null")){
            config.selectedHandlerTemplate = DEFAULT_PYTHON_TEMPLATE_PATH;
        }

        return config.selectedHandlerTemplate;
    }

    public void setCurrentHandlerTemplatePath(String handlerTemplatePath) {
        config.selectedHandlerTemplate = handlerTemplatePath;
    }

    public String getHandlerOutputLocation() {
        if(!config.useCustomPaths){
            String selectedTemplate = config.selectedHandlerTemplate;

            String filename;

            if(selectedTemplate.equals(DEFAULT_PYTHON_TEMPLATE_PATH)){
                filename = "ask_handler.py";
            }
            else if(selectedTemplate.equals(DEFAULT_JAVASCRIPT_TEMPLATE_PATH)){
                filename = "askHandler.js";
            }
            else{
                filename = new File(config.selectedHandlerTemplate).getName();
            }

            return FormatProjectPath(DEFAULT_HANDLER_OUTPUT_PATH + filename);
        }
        return config.handlerOutputPath;
    }

    private String FormatProjectPath(String path) {
        String basePath = _project.getBasePath();

        assert basePath != null;

        return path.replace(PROJECT_ROOT_TOKEN, basePath);
    }

    public void setHandlerOutputLocation(String handlerOutputLocation) {
        config.handlerOutputPath = handlerOutputLocation;
    }

    public String getIntentOutputLocation() {
        return !useCustomPaths() ? FormatProjectPath(DEFAULT_INTENT_OUTPUT_PATH) : config.intentOutputPath;
    }

    public void setIntentOutputLocation(String intentOutputLocation) {
        this.config.intentOutputPath = intentOutputLocation;
    }

    public String getUtterancesOutputLocation() {
        return !useCustomPaths() ? FormatProjectPath(DEFAULT_UTTERANCE_OUTPUT_PATH): config.utterancesOutputPath;
    }

    public void setUtterancesOutputLocation(String utterancesOutputLocation) {
        config.utterancesOutputPath = utterancesOutputLocation;
    }

    public String getCustomTypesOutputLocation() {
        return !useCustomPaths() ? FormatProjectPath(DEFAULT_CUSTOM_TYPES_OUTPUT_PATH): config.customTypesOutputPath;
    }

    public void setCustomTypesOutputLocation(String customTypesOutputLocation) {
        config.customTypesOutputPath = customTypesOutputLocation;
    }

    public boolean hasChanged() {

        boolean configHasChanged =
         config.useCustomPaths != _configCopy.useCustomPaths
//        || !config.templatePaths = _configCopy.templatePaths
        || !config.handlerTemplatePath.equals(_configCopy.handlerTemplatePath)
        || !config.handlerOutputPath.equals(_configCopy.handlerOutputPath)
        || !config.intentOutputPath.equals(_configCopy.intentOutputPath)
        || !config.utterancesOutputPath.equals(_configCopy.utterancesOutputPath)
        || !config.customTypesOutputPath.equals(_configCopy.customTypesOutputPath)
        || !config.selectedHandlerTemplate.equals(_configCopy.selectedHandlerTemplate);

        return configHasChanged;
    }
}
