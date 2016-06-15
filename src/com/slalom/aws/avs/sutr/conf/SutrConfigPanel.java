package com.slalom.aws.avs.sutr.conf;

import com.intellij.openapi.fileChooser.*;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileWrapper;
import com.intellij.ui.ComboboxWithBrowseButton;
import com.slalom.aws.avs.sutr.SutrPluginUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;

/**
 * Created by stryderc on 6/7/2016.
 * <p>
 * Config manager for the Sutr.io plugin.
 */

public class SutrConfigPanel implements Configurable {

    private JPanel myPanel;

    private TextFieldWithBrowseButton handlerOutputLocationBrowseButton;
    private TextFieldWithBrowseButton intentOutputLocationBrowseButton;
    private TextFieldWithBrowseButton utterancesOutputLocationBrowseButton;

    private JCheckBox useCustomOutputPathsCheckBox;
    private JLabel handlerOutputFileLabel;
    private JLabel utterancesOutputFileLabel;
    private JLabel customTypesOutputFileLabel;
    private JLabel intentOutputFileLabel;
    private ComboboxWithBrowseButton handlerTemplateComboBox;
    private TextFieldWithBrowseButton customTypesOutputLocationBrowseButton;

    private SutrConfigProvider configProvider;

    @Nullable
    @Override
    public JComponent createComponent() {

        configProvider = SutrPluginUtil.getConfigProvider();

        SetupValues(configProvider);
        SetupComponents(SutrPluginUtil.getProject());

        return myPanel;
    }

    private void SetupComponents(Project project) {
//        handlerTemplateComboBox.getComboBox()(configProvider.getCurrentHandlerTemplatePath());
        handlerTemplateComboBox.setButtonEnabled(true);
        handlerTemplateComboBox.getComboBox().setEditable(false);

        handlerTemplateComboBox.getComboBox().addActionListener(e -> {
            String handlerTemplateLocation = ((JComboBox) e.getSource()).getSelectedItem().toString();
            configProvider.setCurrentHandlerTemplatePath(handlerTemplateLocation);
            EnableCustomPaths(useCustomOutputPathsCheckBox.isSelected());
        });

        useCustomOutputPathsCheckBox.setSelected(configProvider.useCustomPaths());

        useCustomOutputPathsCheckBox.addActionListener(e -> {
            boolean isSelected = ((JCheckBox) e.getSource()).isSelected();
            EnableCustomPaths(isSelected);
        });

        FileChooserDescriptor fileDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor();
        handlerTemplateComboBox.addBrowseFolderListener(project, fileDescriptor);

        AddFileSelectorHandler(handlerOutputLocationBrowseButton, project, "Handler Output File", "Provide the path where handler output should be saved.");
        AddFileSelectorHandler(intentOutputLocationBrowseButton, project, "SutrIntentModel Output File", "Provide the path where the intentName.json file should be saved.");
        AddFileSelectorHandler(utterancesOutputLocationBrowseButton, project, "Utterances Location", "Provide the path where the utterances file should be saved.");
        AddFileSelectorHandler(customTypesOutputLocationBrowseButton, project, "Custom Types Location", "Provide the path where custom types file should be saved.");
    }

    private void AddDocumentUpdateListener(TextFieldWithBrowseButton handlerOutputLocationBrowseButton, Object o) {

    }

    private void SetupValues(SutrConfigProvider configProvider) {
        for (String path : configProvider.getHandlerTemplateLocations()) {
            this.handlerTemplateComboBox.getComboBox().addItem(path);
            if(configProvider.getCurrentHandlerTemplatePath().equals(path)){
                this.handlerTemplateComboBox.getComboBox().setSelectedItem(path);
            }
        }

        EnableCustomPaths(configProvider.useCustomPaths());
    }

    private void AddFileSelectorHandler(TextFieldWithBrowseButton textFieldWithBrowseButton, Project project, String label, String description) {

        textFieldWithBrowseButton.addActionListener(
            e -> {
                final FileSaverDialog dialog = FileChooserFactory.getInstance().createSaveFileDialog(new FileSaverDescriptor(label, description), myPanel);
                final String path = FileUtil.toSystemIndependentName(getFileName(textFieldWithBrowseButton));
                final int idx = path.lastIndexOf("/");
                VirtualFile baseDir = idx == -1 ? project.getBaseDir() :
                        (LocalFileSystem.getInstance().refreshAndFindFileByIoFile(new File(path.substring(0, idx))));
                baseDir = baseDir == null ? project.getBaseDir() : baseDir;
                final String name = idx == -1 ? path : path.substring(idx + 1);
                final VirtualFileWrapper fileWrapper = dialog.save(baseDir, name);
                if (fileWrapper != null) {
                    textFieldWithBrowseButton.setText(fileWrapper.getFile().getPath());
                }
            }
        );
    }

    private String getFileName(TextFieldWithBrowseButton textFieldWithBrowseButton) {
        return FileUtil.expandUserHome(textFieldWithBrowseButton.getText().trim());
    }

    private void EnableCustomPaths(boolean isEnabled) {

        for (TextFieldWithBrowseButton c : new TextFieldWithBrowseButton[] {
                handlerOutputLocationBrowseButton,
                intentOutputLocationBrowseButton,
                utterancesOutputLocationBrowseButton,
                customTypesOutputLocationBrowseButton
        }) {

            c.setEditable(isEnabled);
            c.setEnabled(isEnabled);
            c.setButtonEnabled(isEnabled);
        }

        handlerOutputFileLabel.setEnabled(isEnabled);
        intentOutputFileLabel.setEnabled(isEnabled);
        utterancesOutputFileLabel.setEnabled(isEnabled);
        customTypesOutputFileLabel.setEnabled(isEnabled);

        configProvider.useCustomPaths(isEnabled);

        handlerOutputLocationBrowseButton.setText(configProvider.getHandlerOutputLocation());
        intentOutputLocationBrowseButton.setText(configProvider.getIntentOutputLocation());
        utterancesOutputLocationBrowseButton.setText(configProvider.getUtterancesOutputLocation());
        customTypesOutputLocationBrowseButton.setText(configProvider.getCustomTypesOutputLocation());

    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Sutr.io";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;  // Figure out what works best here...can we link this to a web page? --stryderc 6/7/2016
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        configProvider.setHandlerOutputLocation(handlerOutputLocationBrowseButton.getText());
        configProvider.setIntentOutputLocation(intentOutputLocationBrowseButton.getText());
        configProvider.setUtterancesOutputLocation(utterancesOutputLocationBrowseButton.getText());
        configProvider.setCustomTypesOutputLocation(customTypesOutputLocationBrowseButton.getText());

        if (configProvider.hasChanged()) {

            configProvider.apply();
        }
    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {

    }

}
