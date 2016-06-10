package com.slalom.aws.avs.sutr.models;

import com.slalom.aws.avs.sutr.mustache.models.SutrIntentModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by stryderc on 1/12/2016.
 */
public class Intents implements Serializable {
    public List<SutrIntentModel> sutrIntentModels;
}
