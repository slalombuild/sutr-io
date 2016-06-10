package com.slalom.aws.avs.sutr.mustache;

import java.io.FileNotFoundException;

/**
 * Created by stryderc on 6/9/2016.
 */
public class SutrMustacheBuilderException extends Throwable {
    public SutrMustacheBuilderException(FileNotFoundException e) {
        super(e);
    }

    public SutrMustacheBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public SutrMustacheBuilderException(String message) {
        super(message);
    }
}
