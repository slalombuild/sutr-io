package com.slalom.aws.avs.sutr.conf;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Created by jordanc on 9/11/2016.
 */
public class SutrRunConfigurationFactory extends ConfigurationFactory {
    private static final String FACTORY_NAME = "Sutr Run Configuration Factory";

    public SutrRunConfigurationFactory(ConfigurationType type) {
        super(type);
    }

    @NotNull
    @Override
    public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new SutrRunConfiguration(project, this, "Sutr");
    }

    @Override
    public String getName() {
        return FACTORY_NAME;
    }
}
