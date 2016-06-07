package com.slalom.aws.avs.sutr.conf;

import com.intellij.ide.DataManager;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by stryderc on 6/7/2016.
 */

public class SutrConfig implements Configurable{

    private JPanel myPanel;

    private TextFieldWithBrowseButton handlerTemplateLocationBrowseButton;
    private TextFieldWithBrowseButton handlerOutputLocationBrowseButton;
    private TextFieldWithBrowseButton intentOutputLocationBrowseButton;
    private TextFieldWithBrowseButton utterancesOutputLocationBrowseButton;

    private JCheckBox useCustomOutputPathsCheckBox;
    private JLabel handlerOutputFileLabel;
    private JLabel utterancesOutputFileLabel;
    private JLabel intentOutputFileLabel;

    private SutrProperties _properties;

    @Nullable
    @Override
    public JComponent createComponent(){

        Project project = getProject();

        PropertiesComponent comp = PropertiesComponent.getInstance(project);

        _properties = new SutrProperties();

        comp.loadFields(_properties);

        FileChooserDescriptor fileDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor();

        handlerTemplateLocationBrowseButton.setText(_properties.handlerTemplateLocation);
        handlerOutputLocationBrowseButton.setText(_properties.handlerOutputLocation);
        intentOutputLocationBrowseButton.setText(_properties.intentOutputLocation);
        utterancesOutputLocationBrowseButton.setText(_properties.utterancesOutputLocation);

        useCustomOutputPathsCheckBox.setSelected(_properties.useCustomPaths);

        handlerTemplateLocationBrowseButton.addBrowseFolderListener("Handler Template File", "Select file", project, fileDescriptor);
        handlerOutputLocationBrowseButton.addBrowseFolderListener("Handler Output File", "Select file", project, fileDescriptor);
        intentOutputLocationBrowseButton.addBrowseFolderListener("Intent Output File", "Select file", project, fileDescriptor);
        utterancesOutputLocationBrowseButton.addBrowseFolderListener("Utterances Location", "Select a directory where the utterances file will be saved.", project, fileDescriptor);

        useCustomOutputPathsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isSelected = ((JCheckBox) e.getSource()).isSelected();
                EnableCustomPaths(isSelected);
            }
        });

        EnableCustomPaths(useCustomOutputPathsCheckBox.isSelected());

        return myPanel;
    }

    private void EnableCustomPaths(boolean isEnabled) {

        handlerOutputFileLabel.setEnabled(isEnabled);
        handlerOutputLocationBrowseButton.setEditable(isEnabled);
        handlerOutputLocationBrowseButton.setButtonEnabled(isEnabled);

        intentOutputFileLabel.setEnabled(isEnabled);
        intentOutputLocationBrowseButton.setEditable(isEnabled);
        intentOutputLocationBrowseButton.setButtonEnabled(isEnabled);

        utterancesOutputFileLabel.setEnabled(isEnabled);
        utterancesOutputLocationBrowseButton.setEditable(isEnabled);
        utterancesOutputLocationBrowseButton.setButtonEnabled(isEnabled);
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

        if (! _handlerOutputLocation.equals(_properties.handlerOutputLocation)
                || !_handlerTemplateLocation.equals(_properties.handlerTemplateLocation)
                || !_intentOutputLocation.equals(_properties.intentOutputLocation)
                || !_utterancesOutputLocation.equals(_properties.utterancesOutputLocation)
                || _useDefaultPaths != _properties.useCustomPaths
                ){

            Project project = getProject();

            PropertiesComponent comp = PropertiesComponent.getInstance(project);

            _properties.handlerTemplateLocation = _handlerTemplateLocation;
            _properties.handlerOutputLocation = _handlerOutputLocation;
            _properties.utterancesOutputLocation = _utterancesOutputLocation;
            _properties.intentOutputLocation = _intentOutputLocation;

            comp.saveFields(_properties);
        }
    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {

    }

    public Project getProject() {
        DataContext dataContext = DataManager.getInstance().getDataContext();  // Deprecated in favor of?? --stryderc 6/7/2016

        return (Project) dataContext.getData(CommonDataKeys.PROJECT.getName());
    }
}
