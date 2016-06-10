package com.slalom.aws.avs.sutr;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.slalom.aws.avs.sutr.conf.SutrConfigProvider;

/**
 * Created by stryderc on 6/8/2016.
 */
public class SutrPluginUtil {

    public static Project getProject() {
        DataContext dataContext = DataManager.getInstance().getDataContextFromFocus().getResult();

        return (Project) dataContext.getData(CommonDataKeys.PROJECT.getName());
    }

    public static SutrConfigProvider getConfigProvider() {

        SutrConfigProvider configProvider = new SutrConfigProvider();

        configProvider.loadProperties(SutrPluginUtil.getProject());

        return configProvider;
    }
}
