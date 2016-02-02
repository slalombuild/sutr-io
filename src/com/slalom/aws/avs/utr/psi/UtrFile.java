package com.slalom.aws.avs.utr.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.slalom.aws.avs.utr.UtrLanguage;
import com.slalom.aws.avs.utr.UtrLanguageType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by stryderc on 10/21/2015.
 */
public class UtrFile extends PsiFileBase {
    public UtrFile(@NotNull FileViewProvider viewProvider){
        super(viewProvider, UtrLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return UtrLanguageType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Utr File";
    }

    @Override
    public Icon getIcon(int flags){
        return super.getIcon(flags);
    }
}
