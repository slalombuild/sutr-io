package com.slalom.aws.avs.sutr.conf;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;

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
    public static final String DEFAULT_PYTHON_TEMPLATE_PATH = "<Default Python>";
    public static final String DEFAULT_JAVASCRIPT_TEMPLATE_PATH = "<Default Javascript>";

    private Project _project;
    private SutrConfig config;

    public void loadProperties(Project project) {
        assert project != null;

        _project = project;

        config = new SutrConfig();

        PropertiesComponent comp = PropertiesComponent.getInstance(project);

        comp.loadFields(config);
    }

    public void apply() {

        PropertiesComponent comp = PropertiesComponent.getInstance(_project);

        comp.saveFields(config);

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

        if(config.templateLocations != null){
            locations.addAll(config.templateLocations);
        }

        return locations;
    }

    public String getCurrentHandlerTemplatePath() {
        if(config.selecteHandlerTemplate == null){
            config.selecteHandlerTemplate = DEFAULT_PYTHON_TEMPLATE_PATH;
        }
        return config.selecteHandlerTemplate;
    }

    public void setCurrentHandlerTemplatePath(String handlerTemplatePath) {
        config.selecteHandlerTemplate = handlerTemplatePath;
    }

    public String getHandlerOutputLocation() {
        if(config.useCustomPaths){
            String selectedTemplate = "";
            String filename = "";

            if(selectedTemplate.equals(DEFAULT_PYTHON_TEMPLATE_PATH)){
                filename = "ask_handler.py";
            }
            else if(selectedTemplate.equals(DEFAULT_JAVASCRIPT_TEMPLATE_PATH)){
                filename = "askHandler.js";
            }

            return FormatProjectPath(DEFAULT_HANDLER_OUTPUT_PATH + filename);
        }
        return config.handlerOutputLocation;
    }

    private String FormatProjectPath(String path) {
        String basePath = _project.getBasePath();

        assert basePath != null;

        return path.replace(PROJECT_ROOT_TOKEN, basePath);
    }

    public void setHandlerOutputLocation(String handlerOutputLocation) {
        config.handlerOutputLocation = handlerOutputLocation;
    }

    public String getIntentOutputLocation() {
        if(useCustomPaths()){
            return FormatProjectPath(DEFAULT_INTENT_OUTPUT_PATH);
        }
        return config.intentOutputLocation;
    }

    public void setIntentOutputLocation(String intentOutputLocation) {
        this.config.intentOutputLocation = intentOutputLocation;
    }

    public String getUtterancesOutputLocation() {
        return useCustomPaths() ? FormatProjectPath(DEFAULT_UTTERANCE_OUTPUT_PATH): config.utterancesOutputLocation;
    }

    public void setUtterancesOutputLocation(String utterancesOutputLocation) {
        config.utterancesOutputLocation = utterancesOutputLocation;
    }
}
