package com.slalom.aws.avs.sutr;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import com.slalom.aws.avs.sutr.psi.SutrTypes;
import org.jetbrains.annotations.NotNull;

class SutrSyntaxHighlighter extends SyntaxHighlighterBase {

    private static final Logger log = Logger.getInstance(SutrSyntaxHighlighter.class);

    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    static final TextAttributesKey COMMENT = TextAttributesKey.createTextAttributesKey("COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};

//    public static final TextAttributesKey BAD_CHARACTER = TextAttributesKey.createTextAttributesKey("BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
//    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};

    static final TextAttributesKey KEYWORD = TextAttributesKey.createTextAttributesKey("KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    private static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[]{KEYWORD};

    static final TextAttributesKey TYPE_NAME = TextAttributesKey.createTextAttributesKey("TYPE_NAME", DefaultLanguageHighlighterColors.IDENTIFIER);
    private static final TextAttributesKey[] TYPE_NAME_KEYS = new TextAttributesKey[]{ TYPE_NAME };

    static final TextAttributesKey CUSTOM_TYPE_ITEM = TextAttributesKey.createTextAttributesKey("CUSTOM_TYPE_ITEM", DefaultLanguageHighlighterColors.CONSTANT);
    private static final TextAttributesKey[] CUSTOM_TYPE_ITEM_KEYS = new TextAttributesKey[]{ CUSTOM_TYPE_ITEM };

    static final TextAttributesKey LITERAL_PHRASE = TextAttributesKey.createTextAttributesKey("LITERAL_PHRASE", DefaultLanguageHighlighterColors.STRING);
    private static final TextAttributesKey[] LITERAL_PHRASE_KEYS = new TextAttributesKey[]{ LITERAL_PHRASE };

    static final TextAttributesKey SUTR_NAME = TextAttributesKey.createTextAttributesKey("SUTR_NAME", DefaultLanguageHighlighterColors.IDENTIFIER);
    private static final TextAttributesKey[] SUTR_NAME_KEYS = new TextAttributesKey[]{ SUTR_NAME };

    static final TextAttributesKey UTTERANCE_PART = TextAttributesKey.createTextAttributesKey("UTTERANCE_PART", DefaultLanguageHighlighterColors.STRING);
    private static final TextAttributesKey[] UTTERANCE_PART_KEYS = new TextAttributesKey[]{ UTTERANCE_PART };

    static final TextAttributesKey SLOT = TextAttributesKey.createTextAttributesKey("SLOT", DefaultLanguageHighlighterColors.NUMBER);
    private static final TextAttributesKey[] SLOT_KEYS = new TextAttributesKey[]{ SLOT };

    static final TextAttributesKey FUNCTION_NAME = TextAttributesKey.createTextAttributesKey("FUNCTION_NAME", DefaultLanguageHighlighterColors.INSTANCE_METHOD);
    private static final TextAttributesKey[] FUNCTION_NAME_KEYS = new TextAttributesKey[]{ FUNCTION_NAME };


    @NotNull
    @Override
    public Lexer getHighlightingLexer() {

        return new SutrLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        log.info("getTokenHighlights determining token type. [" + tokenType.toString() + "]");

        if (tokenType.equals(SutrTypes.SutrCOMMENT)) {
            return COMMENT_KEYS;
        } else if (isKeyword(tokenType)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(SutrTypes.SutrTYPE_NAME)) {
            return TYPE_NAME_KEYS;
        } else if (tokenType.equals(SutrTypes.SutrCUSTOM_TYPE_ITEM)) {
            return CUSTOM_TYPE_ITEM_KEYS;
        } else if (tokenType.equals(SutrTypes.SutrLITERAL_PHRASE)) {
            return LITERAL_PHRASE_KEYS;
        } else if (tokenType.equals(SutrTypes.SutrSUTR_NAME)) {
            return SUTR_NAME_KEYS;
        } else if (tokenType.equals(SutrTypes.SutrUTTERANCE_PART)) {
            return UTTERANCE_PART_KEYS;
        } else if (tokenType.equals(SutrTypes.SutrSLOT)) {
            return SLOT_KEYS;
        } else if (tokenType.equals(SutrTypes.SutrFUNCTION_NAME)) {
            return FUNCTION_NAME_KEYS;
//        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
//            return BAD_CHAR_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }

    private boolean isKeyword(IElementType tokenType) {
        return tokenType.equals(SutrTypes.SutrLITERAL)
                ||tokenType.equals(SutrTypes.SutrTYPE)
                ||tokenType.equals(SutrTypes.SutrDEF)
                ||tokenType.equals(SutrTypes.SutrFROM)
                ||tokenType.equals(SutrTypes.SutrIMPORT);
    }
}
