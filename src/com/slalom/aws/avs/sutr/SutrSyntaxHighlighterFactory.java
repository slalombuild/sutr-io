package com.slalom.aws.avs.sutr;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Stryder on 10/22/2015.
 */
public class SutrSyntaxHighlighterFactory extends SyntaxHighlighterFactory {

    private static final String TAG = "SutrSyntaxHighlighterFactory";
    private static final Logger log = Logger.getInstance(SutrSyntaxHighlighterFactory.class);

    @NotNull
    @Override
    public SyntaxHighlighter getSyntaxHighlighter(Project project, VirtualFile virtualFile){
        log.info("Creating new Sutr Syntax Highlighter for project [" + project.getName() + "]");

        return new SutrSyntaxHighlighter();
    }
}
