package com.slalom.aws.avs.utr;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class UtrLexerAdapter extends FlexAdapter {
    public UtrLexerAdapter(){
        super(new _UtrLexer((Reader) null));
    }
}
