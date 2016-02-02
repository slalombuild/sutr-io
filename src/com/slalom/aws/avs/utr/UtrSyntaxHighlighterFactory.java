package com.slalom.aws.avs.utr;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Stryder on 10/22/2015.
 */
public class UtrSyntaxHighlighterFactory extends SyntaxHighlighterFactory {
    private static final Logger log = Logger.getInstance(UtrSyntaxHighlighterFactory.class);

    @NotNull
    @Override
    public SyntaxHighlighter getSyntaxHighlighter(Project project, VirtualFile virtualFile){
        log.info("Creating new Utr Syntax Highlight for [" + project.getName() + "]");

        return new UtrSyntaxHighlighter();
    }
}
