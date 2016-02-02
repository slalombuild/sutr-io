package com.slalom.aws.avs.sutr;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Created by stryderc on 10/21/2015.
 */
public class SutrFileTypeFactory extends FileTypeFactory {

    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer){
        fileTypeConsumer.consume(SutrLanguageType.INSTANCE, "sutr");
    }
}
