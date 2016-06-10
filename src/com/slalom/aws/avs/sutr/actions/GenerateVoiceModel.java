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
            SutrConfigProvider sutrConfigProvider = SutrPluginUtil.getConfigProvider();

            final StringBuilder buildIntent = SutrGenerator.buildIntent(sutrFiles);
            final StringBuilder buildUtterances = SutrGenerator.buildUtterances(sutrFiles);
            final StringBuilder buildHandler = SutrGenerator.buildHandler(sutrFiles, sutrConfigProvider.getCurrentHandlerTemplatePath());

            String handlerPath = sutrConfigProvider.getHandlerOutputLocation();
            String intentPath = sutrConfigProvider.getIntentOutputLocation();
            String utterancesPath = sutrConfigProvider.getUtterancesOutputLocation();

            WriteContentToFile(buildHandler, handlerPath);
            WriteContentToFile(buildIntent, intentPath);
            WriteContentToFile(buildUtterances, utterancesPath);

            ActionUtil.ShowInfoMessage("ASK model generated.", e);

        } catch (SutrGeneratorException e1) {
            ActionUtil.ShowErrorMessage(e1.getMessage(), e);
        }
    }

    private void WriteContentToFile(StringBuilder fileContent, String filePath) throws SutrGeneratorException {

        File file = new File(filePath);

        try {
            File dir = Paths.get(filePath).getParent().toFile();

            boolean dirExists = dir.isDirectory();
            if(!dirExists){
               dirExists =  dir.mkdirs();
            }

            if(!dirExists){
                throw new SutrGeneratorException("Unable to create directory [" + dir.toString() + "]");
            }

            if((file.exists()|| file.createNewFile())){
                FileWriter writer = new FileWriter(file);
                writer.write(fileContent.toString());
                writer.close();
            }
            else{
                throw new SutrGeneratorException("Unable to create file [" + file.toPath().toString() + "]");
            }


        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

}
