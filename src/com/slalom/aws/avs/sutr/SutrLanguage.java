package com.slalom.aws.avs.sutr;

import com.intellij.lang.Language;

/**
 * Created by stryderc on 10/21/2015.
 */
public class SutrLanguage extends Language {
    public static final SutrLanguage INSTANCE = new SutrLanguage();

    private SutrLanguage(){
        super("sutr");
    }
}
