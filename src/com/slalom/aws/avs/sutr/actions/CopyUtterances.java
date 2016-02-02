package com.slalom.aws.avs.sutr.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.Project;
import com.slalom.aws.avs.sutr.actions.exceptions.SutrGeneratorException;
import com.slalom.aws.avs.sutr.psi.SutrFile;

import java.awt.datatransfer.StringSelection;
import java.util.List;

/**
 * Created by stryderc on 1/12/2016.
 */
public class CopyUtterances extends SutrAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = getEventProject(e);
        final List<SutrFile> sutrFiles = ActionUtil.getFiles(e);
        if (project == null || sutrFiles.isEmpty()) return;

        try {
            final StringBuilder builder = SutrGenerator.buildUtterances(sutrFiles);
            CopyPasteManager.getInstance().setContents(new StringSelection(builder.toString()));
            ActionUtil.ShowInfoMessage("Sutr Utterances copied to clipboard", e);
        } catch (SutrGeneratorException e1) {
            ActionUtil.ShowErrorMessage(e1.getMessage(), e);
        }


    }
}
