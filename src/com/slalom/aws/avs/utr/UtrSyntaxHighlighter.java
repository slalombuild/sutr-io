package com.slalom.aws.avs.utr;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.slalom.aws.avs.utr.psi.UtrTypes;
import org.jetbrains.annotations.NotNull;

public class UtrSyntaxHighlighter extends SyntaxHighlighterBase {

    private static final Logger log = Logger.getInstance(UtrSyntaxHighlighter.class);

    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    public static final TextAttributesKey BAD_CHARACTER = TextAttributesKey.createTextAttributesKey("BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};

    public static final TextAttributesKey INTENT = TextAttributesKey.createTextAttributesKey("INTENT", DefaultLanguageHighlighterColors.INSTANCE_METHOD);
    private static final TextAttributesKey[] INTENT_KEYS = new TextAttributesKey[]{INTENT};

    public static final TextAttributesKey WORD = TextAttributesKey.createTextAttributesKey("WORD", HighlighterColors.TEXT);
    private static final TextAttributesKey[] WORD_KEYS = new TextAttributesKey[]{ WORD };

    public static final TextAttributesKey LITERAL_PHRASE = TextAttributesKey.createTextAttributesKey("LITERAL_PHRASE", DefaultLanguageHighlighterColors.STRING);
    private static final TextAttributesKey[] LITERAL_PHRASE_KEYS = new TextAttributesKey[]{ LITERAL_PHRASE };

    public static final TextAttributesKey SLOT = TextAttributesKey.createTextAttributesKey("SLOT", DefaultLanguageHighlighterColors.NUMBER);
    private static final TextAttributesKey[] SLOT_KEYS = new TextAttributesKey[]{ SLOT };


    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        log.info("Creating new UtrLexerAdapter.");
        return new UtrLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {

        log.info("Attempting to get TokenHighlights for token [" + tokenType.toString() + "]");
        
        if (tokenType.equals(UtrTypes.UtrINTENT)) {
            return INTENT_KEYS;
        } else if (tokenType.equals(UtrTypes.UtrWORD)) {
            return WORD_KEYS;
        } else if (tokenType.equals(UtrTypes.UtrPHRASE)) {
            return LITERAL_PHRASE_KEYS;
        } else if (tokenType.equals(UtrTypes.UtrSLOT)) {
            return SLOT_KEYS;
        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}
