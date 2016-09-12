package com.slalom.aws.avs.sutr.conf;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.slalom.aws.avs.sutr.SutrIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by jordanc on 9/11/2016.
 */
public class SutrRunConfigType implements ConfigurationType {

    @Override
    public String getDisplayName() {
        return "Sutr";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "Configures and generates a Sutr Ask model";
    }

    @Override
    public Icon getIcon() {
        return SutrIcons.FILE;
    }

    @NotNull
    @Override
    public String getId() {
        return "SUTR_RUN_CONFIGURATION";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{new SutrRunConfigurationFactory(this)};
    }
}
