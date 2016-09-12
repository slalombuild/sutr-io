package com.slalom.aws.avs.sutr.conf;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by jordanc on 9/11/2016.
 */
public class SutrRunConfigSettingsEditor extends SettingsEditor<SutrRunConfiguration> {
    private JPanel myPanel;
    private JLabel sutrFilesLabel;
    private JTextField sutrFilesTextField;

    @Override
    protected void resetEditorFrom(@NotNull SutrRunConfiguration sutrRunConfiguration) {
        sutrFilesTextField.setText(sutrRunConfiguration.getIncludedSutrFilePattern());
    }

    @Override
    protected void applyEditorTo(@NotNull SutrRunConfiguration sutrRunConfiguration) throws ConfigurationException {
        sutrRunConfiguration.setIncludedSutrFilePattern(sutrFilesTextField.getText());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return myPanel;
    }
}
