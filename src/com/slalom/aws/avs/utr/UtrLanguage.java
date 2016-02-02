package com.slalom.aws.avs.utr;

import com.intellij.lang.Language;

/**
 * Created by stryderc on 10/21/2015.
 */
public class UtrLanguage extends Language {
    public static final UtrLanguage INSTANCE = new UtrLanguage();

    private UtrLanguage(){
        super("utr");
    }
}
