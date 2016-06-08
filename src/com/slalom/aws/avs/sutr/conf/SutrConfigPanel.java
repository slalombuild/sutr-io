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

    private TextFieldWithBrowseButton handlerTemplateLocationBrowseButton;
    private TextFieldWithBrowseButton handlerOutputLocationBrowseButton;
    private TextFieldWithBrowseButton intentOutputLocationBrowseButton;
    private TextFieldWithBrowseButton utterancesOutputLocationBrowseButton;

    private JCheckBox useCustomOutputPathsCheckBox;
    private JLabel handlerOutputFileLabel;
    private JLabel utterancesOutputFileLabel;
    private JLabel intentOutputFileLabel;
    private JLabel sutrErrorLabel;

    private SutrConfig _properties;

    @Nullable
    @Override
    public JComponent createComponent() {

        _properties = SutrPluginUtil.getConfig();

        handlerTemplateLocationBrowseButton.setText(_properties.handlerTemplateLocation);

        useCustomOutputPathsCheckBox.setSelected(_properties.useCustomPaths);
        useCustomOutputPathsCheckBox.addActionListener(e -> {
            boolean isSelected = ((JCheckBox) e.getSource()).isSelected();
            EnableCustomPaths(isSelected);
        });

        AddBrowserHandlers(SutrPluginUtil.getProject());

        EnableCustomPaths(useCustomOutputPathsCheckBox.isSelected());

        return myPanel;
    }

    private void AddBrowserHandlers(Project project) {
        FileChooserDescriptor fileDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor();

        handlerTemplateLocationBrowseButton.addBrowseFolderListener("Handler Template File", "Select handler template file", project, fileDescriptor);

        AddFileSelectorHandler(handlerOutputLocationBrowseButton, project, "Handler Output File", "Provide the path and file name where handler output should be saved.");
        AddFileSelectorHandler(intentOutputLocationBrowseButton, project, "Intent Output File", "Provide the path and file name where the intent.json should be saved.");
        AddFileSelectorHandler(utterancesOutputLocationBrowseButton, project, "Utterances Location", "Provide the path and file name where utterances should be saved.");

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

        for (TextFieldWithBrowseButton c : new TextFieldWithBrowseButton[] {handlerOutputLocationBrowseButton, intentOutputLocationBrowseButton, utterancesOutputLocationBrowseButton}) {

            c.setEditable(isEnabled);
            c.setEnabled(isEnabled);
            c.setButtonEnabled(isEnabled);
        }

        handlerOutputFileLabel.setEnabled(isEnabled);
        intentOutputFileLabel.setEnabled(isEnabled);
        utterancesOutputFileLabel.setEnabled(isEnabled);

        if(isEnabled){
            handlerOutputLocationBrowseButton.setText(_properties.handlerOutputLocation);
            intentOutputLocationBrowseButton.setText(_properties.intentOutputLocation);
            utterancesOutputLocationBrowseButton.setText(_properties.utterancesOutputLocation);
        }
        else{
            handlerOutputLocationBrowseButton.setText(_properties.defaultHandlerOutputLocation);
            intentOutputLocationBrowseButton.setText(_properties.defaultIntentOutputLocation);
            utterancesOutputLocationBrowseButton.setText(_properties.defaultUtterancesOutputLocation);
        }
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
        String _handlerTemplateLocation = handlerTemplateLocationBrowseButton.getText();
        String _handlerOutputLocation = handlerOutputLocationBrowseButton.getText();
        String _utterancesOutputLocation = utterancesOutputLocationBrowseButton.getText();
        String _intentOutputLocation = intentOutputLocationBrowseButton.getText();

        Boolean _useDefaultPaths = useCustomOutputPathsCheckBox.isSelected();

        if (!_handlerOutputLocation.equals(_properties.handlerOutputLocation)
                || !_handlerTemplateLocation.equals(_properties.handlerTemplateLocation)
                || !_intentOutputLocation.equals(_properties.intentOutputLocation)
                || !_utterancesOutputLocation.equals(_properties.utterancesOutputLocation)
                || _useDefaultPaths != _properties.useCustomPaths
                ) {

            _properties.handlerTemplateLocation = _handlerTemplateLocation;
            _properties.handlerOutputLocation = _handlerOutputLocation;
            _properties.utterancesOutputLocation = _utterancesOutputLocation;
            _properties.intentOutputLocation = _intentOutputLocation;
            _properties.useCustomPaths = _useDefaultPaths;

            SutrPluginUtil.saveConfig(_properties);
        }
    }



    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {

    }

}
