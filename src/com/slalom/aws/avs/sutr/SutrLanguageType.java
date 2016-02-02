package com.slalom.aws.avs.sutr;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SutrLanguageType extends LanguageFileType {
    public static final SutrLanguageType INSTANCE = new SutrLanguageType();

    private SutrLanguageType(){
        super(SutrLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Slalom Utterance file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Slalom Utterance language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "sutr";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return SutrIcons.FILE;
    }
}
