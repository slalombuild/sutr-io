package com.slalom.aws.avs.sutr;

import com.intellij.ide.DataManager;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.slalom.aws.avs.sutr.conf.SutrConfig;

/**
 * Created by stryderc on 6/8/2016.
 */
public class SutrPluginUtil {

    public static Project getProject() {
        DataContext dataContext = DataManager.getInstance().getDataContextFromFocus().getResult();

        return (Project) dataContext.getData(CommonDataKeys.PROJECT.getName());
    }

    public static SutrConfig getConfigProvider() {

        Project project = SutrPluginUtil.getProject();

        PropertiesComponent comp = PropertiesComponent.getInstance(project);

        SutrConfig properties = new SutrConfig();

        comp.loadFields(properties);


        return properties;
    }

    public static void saveConfig(SutrConfig properties){

        Project project = SutrPluginUtil.getProject();

        PropertiesComponent comp = PropertiesComponent.getInstance(project);

        comp.saveFields(properties);

    }
}
