package com.slalom.aws.avs.sutr.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ide.CopyPasteManager;
import com.slalom.aws.avs.sutr.actions.exceptions.SutrGeneratorException;
import com.slalom.aws.avs.sutr.psi.SutrCustomType;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.StringSelection;

/**
 * Created by stryderc on 1/12/2016.
 */
public class CopyCustomType extends SutrAction {

    @Override
    public void update(@NotNull final AnActionEvent e) {
        boolean enabled = ActionUtil.getCustomTypeUnderCaret(e) != null;
        e.getPresentation().setEnabled(enabled);
        e.getPresentation().setVisible(enabled);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        SutrCustomType customType = ActionUtil.getCustomTypeUnderCaret(e);
        if (customType == null) {
            ActionUtil.ShowErrorMessage("No type name found.", e);
            return;
        }

        try {
            final StringBuilder result = SutrGenerator.buildCustomTypeItems(customType);
            CopyPasteManager.getInstance().setContents(new StringSelection(result.toString()));
            ActionUtil.ShowInfoMessage("Custom types list for " + customType.getTypeName().getText() + " copied to clipboard", e);
        } catch (SutrGeneratorException e1) {
            ActionUtil.ShowErrorMessage(e1.getMessage(), e);
        }
    }
}
