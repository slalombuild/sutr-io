package com.slalom.aws.avs.sutr.conf;

import com.intellij.ide.DataManager;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;

/**
 * Created by stryderc on 6/7/2016.
 */
class SutrOutputState {

    private static final String SUTR_HANDLER_OUTPUT_LOCATION = "sutr.handlerOutputLocation";
    private static final String SUTR_HANDLER_TEMPLATE_LOCATION = "sutr.handlerTemplateLocation";
    private static final String SUTR_INTENT_OUTPUT_LOCATION = "sutr.intentOutputLocation";
    private static final String SUTR_UTTERANCES_OUTPUT_LOCATION = "sutr.utterancesOutputLocation";

    SutrOutputState() {

        PropertiesComponent comp = getPropertiesComponent();

        handlerOutputLocation = comp.getValue(SUTR_HANDLER_OUTPUT_LOCATION);
        handlerTemplateLocation = comp.getValue(SUTR_HANDLER_TEMPLATE_LOCATION);
        intentOutputLocation = comp.getValue(SUTR_INTENT_OUTPUT_LOCATION);
        utterancesOutputLocation = comp.getValue(SUTR_UTTERANCES_OUTPUT_LOCATION);
    }

    private PropertiesComponent getPropertiesComponent() {

        DataContext dataContext = DataManager.getInstance().getDataContext();  // Deprecated in favor of?? --stryderc 6/7/2016

        Project project = (Project) dataContext.getData(CommonDataKeys.PROJECT.getName());

        assert project != null; // throw an exception, log it, show a message? --stryderc 6/7/2016

        return PropertiesComponent.getInstance(project);
    }

    void Save() {
        PropertiesComponent comp = getPropertiesComponent();

        comp.setValue(SUTR_HANDLER_OUTPUT_LOCATION, handlerOutputLocation);
        comp.setValue(SUTR_HANDLER_TEMPLATE_LOCATION, handlerTemplateLocation);
        comp.setValue(SUTR_INTENT_OUTPUT_LOCATION, intentOutputLocation);
        comp.setValue(SUTR_UTTERANCES_OUTPUT_LOCATION, utterancesOutputLocation);
    }

    String handlerOutputLocation;
    String handlerTemplateLocation;
    String intentOutputLocation;
    String utterancesOutputLocation;
}