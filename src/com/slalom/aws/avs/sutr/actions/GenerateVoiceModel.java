package com.slalom.aws.avs.sutr.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.slalom.aws.avs.sutr.SutrPluginUtil;
import com.slalom.aws.avs.sutr.actions.exceptions.SutrGeneratorException;
import com.slalom.aws.avs.sutr.conf.SutrConfig;
import com.slalom.aws.avs.sutr.psi.SutrFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
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
            SutrConfig sutrConfig = SutrPluginUtil.getConfig();

            final StringBuilder buildIntent = SutrGenerator.buildIntent(sutrFiles);
            final StringBuilder buildUtterances = SutrGenerator.buildUtterances(sutrFiles);
            final StringBuilder buildHandler = SutrGenerator.buildHandler(sutrFiles, sutrConfig.handlerTemplateLocation);

            String handlerPath;
            String intentPath;
            String utterancesPath;

            if(sutrConfig.useCustomPaths){
                handlerPath = sutrConfig.handlerOutputLocation;
                intentPath = sutrConfig.intentOutputLocation;
                utterancesPath = sutrConfig.utterancesOutputLocation;
            }
            else{
                String basePath = project.getBasePath();
                if(basePath == null){
                    throw new SutrGeneratorException("Unable to locate the project base path");
                }

                handlerPath = sutrConfig.defaultHandlerOutputLocation.replace("$PROJECT_ROOT$", basePath);
                intentPath = sutrConfig.intentOutputLocation.replace("$PROJECT_ROOT$", basePath);
                utterancesPath = sutrConfig.utterancesOutputLocation.replace("$PROJECT_ROOT$", basePath);
            }

            WriteContentToFile(buildHandler, handlerPath);
            WriteContentToFile(buildIntent, intentPath);
            WriteContentToFile(buildUtterances, utterancesPath);



        } catch (SutrGeneratorException e1) {
            ActionUtil.ShowErrorMessage(e1.getMessage(), e);
        }
    }

    private void WriteContentToFile(StringBuilder fileContent, String filePath) {
        Path path = Paths.get(filePath);
        Path fileName = path.getFileName();
        Path dirs = path.getParent();

        File file = new File(filePath);

        try {

            if(file.exists()|| file.createNewFile()){
                FileWriter writer = new FileWriter(file);
                writer.write(fileContent.toString());
                writer.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

}
