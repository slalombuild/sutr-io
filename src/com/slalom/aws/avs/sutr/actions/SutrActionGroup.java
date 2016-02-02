package com.slalom.aws.avs.sutr.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.slalom.aws.avs.sutr.psi.SutrFile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Stryder on 1/24/2016.
 */
public class SutrActionGroup extends DefaultActionGroup {
    @Override
    public void update(@NotNull final AnActionEvent e) {
        List<SutrFile> sutrFiles = ActionUtil.getFiles(e);
        boolean enabled = !sutrFiles.isEmpty();
        e.getPresentation().setEnabled(enabled);
        e.getPresentation().setVisible(enabled);
    }
}
