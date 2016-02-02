package com.slalom.aws.avs.sutr;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class SutrLexerAdapter extends FlexAdapter {
    public SutrLexerAdapter(){
        super(new _SutrLexer((Reader) null));
    }
}
