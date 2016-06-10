package com.slalom.aws.avs.sutr.mustache.models;

import java.util.List;

/**
 * Created by stryderc on 1/12/2016.
 */
public class SutrIntentModel {
    public String intentName;
    public List<SutrSlotModel> slots;
    public String functionName;
    public boolean first;

    public SutrIntentModel(String intent_name, List<SutrSlotModel> slots) {
        this.intentName = intent_name;
        this.slots = slots;
    }
}
