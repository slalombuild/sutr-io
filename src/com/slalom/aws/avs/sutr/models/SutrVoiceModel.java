package com.slalom.aws.avs.sutr.models;

import com.slalom.aws.avs.sutr.psi.SutrObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stryderc on 6/9/2016.
 */
public class SutrVoiceModel  {
    List<SutrObject> sutrObjects;
    public SutrVoiceModel(List<SutrObject> sutrObjects) {
        this.sutrObjects = sutrObjects;
    }

    public SutrVoiceModel() {
        sutrObjects = new ArrayList<>();
    }

    public List<SutrObject> getSutrObjects() {
        return sutrObjects;
    }

    public void setSutrObjects(List<SutrObject> sutrObjects) {
        this.sutrObjects = sutrObjects;
    }
}
