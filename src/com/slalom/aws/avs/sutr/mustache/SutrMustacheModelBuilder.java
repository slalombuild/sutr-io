package com.slalom.aws.avs.sutr.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.slalom.aws.avs.sutr.actions.SutrGenerator;
import com.slalom.aws.avs.sutr.actions.exceptions.SutrGeneratorException;
import com.slalom.aws.avs.sutr.conf.SutrConfigProvider;
import com.slalom.aws.avs.sutr.mustache.models.SutrIntentModel;
import com.slalom.aws.avs.sutr.mustache.models.SutrMustacheModel;
import com.slalom.aws.avs.sutr.psi.SutrFile;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Objects;

/**
 * Created by stryderc on 6/9/2016.
 */
public class SutrMustacheModelBuilder {

    private SutrMustacheModel _mustacheModel;
    private String _template;

    public SutrMustacheModelBuilder(String template) {
        _template = template;
        _mustacheModel = new SutrMustacheModel();
    }

    public void Build(List<SutrFile> sutrFiles) throws SutrMustacheBuilderException, SutrGeneratorException {

        SutrGenerator.BuildSutrDefinitions sutrDefinitions = new SutrGenerator.BuildSutrDefinitions(sutrFiles).invoke();

        if (sutrDefinitions.getSutrObjectList().isEmpty()) {
            throw new SutrGeneratorException("No Sutr definitions found");
        }

        List<SutrIntentModel> sutrCollection = SutrGenerator.GetModels(sutrDefinitions);

        _mustacheModel.setSutrIntents(sutrCollection);
    }

    public String Compile() throws SutrMustacheBuilderException {

        FileReader reader = null;
        try {
            reader = new FileReader(_template);
        } catch (FileNotFoundException e) {
            throw new SutrMustacheBuilderException("Unable to locate template file", e);
        }

        StringWriter sw = new StringWriter();

        MustacheFactory mf = new DefaultMustacheFactory();

        Mustache mustache = mf.compile(reader, "Handler Template");

        mustache.execute(sw, this._mustacheModel);

        String result = sw.toString();

        return result;
    }
}
