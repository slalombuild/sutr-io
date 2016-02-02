package com.slalom.aws.avs.utr;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class UtrLanguageType extends LanguageFileType {
    public static final UtrLanguageType INSTANCE = new UtrLanguageType();

    private UtrLanguageType(){
        super(UtrLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Amazon Utterance file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Amazon Utterance language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "utr";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return UtrIcons.FILE;
    }
}
