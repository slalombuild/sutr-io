package com.slalom.aws.avs.sutr.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.slalom.aws.avs.sutr.actions.exceptions.SutrGeneratorException;
import com.slalom.aws.avs.sutr.psi.SutrFile;

import java.util.List;

/**
 * Created by stryderc on 6/7/2016.
 */
public class GenerateVoiceModel extends SutrAction {

    private static final Logger log = Logger.getInstance(CopyIntentJson.class);
    private static final String TAG = "Generate Voice Model";

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = getEventProject(e);
        final List<SutrFile> sutrFiles = ActionUtil.getFiles(e);
        if (project == null || sutrFiles.isEmpty()) return;

        try {
            final StringBuilder buildIntent = SutrGenerator.buildIntent(sutrFiles);
            final StringBuilder buildUtterances = SutrGenerator.buildUtterances(sutrFiles);
            String template = null;
            StringBuilder buildHandler = SutrGenerator.buildHandler(sutrFiles, template);

            ActionUtil.ShowInfoMessage("Sutr Intent definition copied to clipboard", e);
        } catch (SutrGeneratorException e1) {
            ActionUtil.ShowErrorMessage(e1.getMessage(), e);
        }
    }

}
