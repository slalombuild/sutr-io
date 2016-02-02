package com.slalom.aws.avs.sutr.models;

import java.util.List;

/**
 * Created by stryderc on 1/12/2016.
 */
public class Intent {
    public String intent;
    public List<Slot> slots;

    public Intent(String intent_name, List<Slot> slots) {
        this.intent = intent_name;
        this.slots = slots;
    }
}
