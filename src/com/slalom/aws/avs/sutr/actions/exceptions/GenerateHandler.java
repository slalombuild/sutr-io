package com.slalom.aws.avs.sutr.actions.exceptions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.Project;
import com.slalom.aws.avs.sutr.SutrPluginUtil;
import com.slalom.aws.avs.sutr.actions.ActionUtil;
import com.slalom.aws.avs.sutr.actions.SutrAction;
import com.slalom.aws.avs.sutr.conf.SutrConfigProvider;
import com.slalom.aws.avs.sutr.mustache.SutrMustacheBuilderException;
import com.slalom.aws.avs.sutr.mustache.SutrMustacheModelBuilder;
import com.slalom.aws.avs.sutr.psi.SutrFile;

import java.awt.datatransfer.StringSelection;
import java.util.List;

/**
 * Created by stryderc on 6/9/2016.
 */
public class GenerateHandler extends SutrAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = getEventProject(e);
        final List<SutrFile> sutrFiles = ActionUtil.getFiles(e);
        if (project == null || sutrFiles.isEmpty()) return;

        try {
            SutrConfigProvider config = SutrPluginUtil.getConfigProvider();

//            String handlerTemplateLocation = config.handlerTemplateLocation;

//            String template = config.getCurrentHandlerTemplatePath();

            String template =  "C:\\Users\\stryderc\\dev\\sources\\sutr-io\\src\\resources\\templates\\python.mustache";

            SutrMustacheModelBuilder modelBuilder = new SutrMustacheModelBuilder(template);
            modelBuilder.Build(sutrFiles);
            StringSelection transferable = new StringSelection(modelBuilder.Compile());

            CopyPasteManager.getInstance().setContents(transferable);

        } catch (SutrMustacheBuilderException | SutrGeneratorException e1) {
            e1.printStackTrace();
        }
    }

}
