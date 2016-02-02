package com.slalom.aws.avs.sutr.psi.impl;

import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Stryder on 1/23/2016.
 */
public class SlotLookupElement extends LookupElement {
    private final String _slotName;

    public SlotLookupElement(final String text) {
        _slotName = text;
    }


    @NotNull
    @Override
    public String getLookupString() {
        return _slotName;
    }

    @Override
    public void handleInsert(final InsertionContext context) {
        final Editor editor = context.getEditor();
        final Document document = editor.getDocument();
        context.commitDocument();
        document.insertString(context.getTailOffset(), "} ");
        
        editor.getCaretModel().moveToOffset(context.getSelectionEndOffset());
    }



    @Override
    public void renderElement(final LookupElementPresentation presentation) {

        super.renderElement(presentation);
    }
}
