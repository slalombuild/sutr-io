package com.slalom.aws.avs.sutr.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.slalom.aws.avs.sutr.SutrPluginUtil;
import com.slalom.aws.avs.sutr.actions.exceptions.SutrGeneratorException;
import com.slalom.aws.avs.sutr.conf.SutrConfigProvider;
import com.slalom.aws.avs.sutr.psi.SutrFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
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
            SutrGenerator.genererateAsk(sutrFiles);
            ActionUtil.ShowInfoMessage("ASK model generated.", e);

        } catch (SutrGeneratorException e1) {
            ActionUtil.ShowErrorMessage(e1.getMessage(), e);
        }
    }
}
