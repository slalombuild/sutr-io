package com.slalom.aws.avs.sutr.mustache.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stryderc on 6/9/2016.
 */
public class SutrMustacheModel {
    List<SutrIntentModel> sutrIntents;

    public SutrMustacheModel(List<SutrIntentModel> sutrIntents) {
        this.sutrIntents = sutrIntents;
    }

    public SutrMustacheModel() {
        sutrIntents = new ArrayList<>();
    }

    public List<SutrIntentModel> getSutrIntents() {
        return sutrIntents;
    }

    public void setSutrIntents(List<SutrIntentModel> sutrObjects) {
        this.sutrIntents = sutrObjects;
    }
}
