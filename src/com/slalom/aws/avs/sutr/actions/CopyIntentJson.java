package com.slalom.aws.avs.sutr.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.Project;
import com.slalom.aws.avs.sutr.actions.exceptions.SutrGeneratorException;
import com.slalom.aws.avs.sutr.psi.SutrFile;

import java.awt.datatransfer.StringSelection;
import java.util.List;

/**
 * Created by stryderc on 1/12/2016.
 */
public class CopyIntentJson extends SutrAction {

    private static final Logger log = Logger.getInstance(CopyIntentJson.class);
    private static final String TAG = "CopyIntentJson";

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = getEventProject(e);
        final List<SutrFile> sutrFiles = ActionUtil.getFiles(e);
        if (project == null || sutrFiles.isEmpty()) return;

        try {
            StringBuilder result = SutrGenerator.buildIntent(sutrFiles);
            CopyPasteManager.getInstance().setContents(new StringSelection(result.toString()));
            ActionUtil.ShowInfoMessage("Sutr Intent definition copied to clipboard", e);
        } catch (SutrGeneratorException e1) {
            ActionUtil.ShowErrorMessage(e1.getMessage(), e);
        }
    }
}
