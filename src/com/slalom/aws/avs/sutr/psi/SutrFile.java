package com.slalom.aws.avs.sutr.psi;

import com.slalom.aws.avs.sutr.SutrLanguage;
import com.slalom.aws.avs.sutr.SutrLanguageType;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by stryderc on 10/21/2015.
 */
public class SutrFile extends PsiFileBase {
    public SutrFile(@NotNull FileViewProvider viewProvider){
        super(viewProvider, SutrLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return SutrLanguageType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Sutr File";
    }

    @Override
    public Icon getIcon(int flags){
        return super.getIcon(flags);
    }
}
