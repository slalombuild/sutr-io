package com.slalom.aws.avs.sutr.conf;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by stryderc on 6/7/2016.
 */

public class SutrConfig implements Configurable{

    private JPanel myPanel;

    private TextFieldWithBrowseButton handlerTemplateLocation;
    private TextFieldWithBrowseButton handlerOutputLocation;
    private TextFieldWithBrowseButton intentOutputLocation;
    private TextFieldWithBrowseButton utterancesOutputLocation;

    @Nullable
    @Override
    public JComponent createComponent(){

        SutrOutputState state = new SutrOutputState();

        handlerOutputLocation.setText(state.handlerOutputLocation);
        handlerTemplateLocation.setText(state.handlerTemplateLocation);
        intentOutputLocation.setText(state.intentOutputLocation);
        utterancesOutputLocation.setText(state.utterancesOutputLocation);

        return myPanel;
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
        SutrOutputState state = new SutrOutputState();

        return (! handlerOutputLocation.getText().equals(state.handlerOutputLocation)
                || !handlerTemplateLocation.getText().equals(state.handlerTemplateLocation)
                || !intentOutputLocation.getText().equals(state.intentOutputLocation)
                || ! utterancesOutputLocation.getText().equals(state.utterancesOutputLocation));
    }

    @Override
    public void apply() throws ConfigurationException {

        SutrOutputState state = new SutrOutputState();

        String _handlerTemplateLocation = handlerTemplateLocation.getText();
        String _handlerOutputLocation = handlerOutputLocation.getText();
        String _utterancesOutputLocation = utterancesOutputLocation.getText();
        String _intentOutputLocation = intentOutputLocation.getText();

        if (isModified()){

            state.handlerOutputLocation = _handlerOutputLocation;
            state.handlerTemplateLocation = _handlerTemplateLocation;
            state.intentOutputLocation = _intentOutputLocation;
            state.utterancesOutputLocation = _utterancesOutputLocation;

            state.Save();
        }
    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {

    }
}
