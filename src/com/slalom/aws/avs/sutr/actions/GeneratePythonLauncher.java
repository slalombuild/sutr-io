package com.slalom.aws.avs.sutr.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.Project;
import com.slalom.aws.avs.sutr.actions.exceptions.SutrGeneratorException;
import com.slalom.aws.avs.sutr.psi.SutrFile;

import java.awt.datatransfer.StringSelection;
import java.util.List;

/**
 * Created by firep on 1/20/2016.
 */
public class GeneratePythonLauncher extends SutrAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = getEventProject(e);
        final List<SutrFile> sutrFiles = ActionUtil.getFiles(e);
        if (project == null || sutrFiles.isEmpty()) return;

        StringBuilder builder = null;

        try {
            builder = SutrGenerator.buildPythonLauncher(sutrFiles);
            CopyPasteManager.getInstance().setContents(new StringSelection(builder.toString()));
        } catch (SutrGeneratorException e1) {
            ActionUtil.ShowErrorMessage(e1.getMessage(), e);
        }

    }
}
