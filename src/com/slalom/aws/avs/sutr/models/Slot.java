package com.slalom.aws.avs.sutr.models;

/**
 * Created by stryderc on 1/12/2016.
 */
public class Slot {

    private final String defaultValue;
    public boolean isFirst;

    public Slot(String name, String type){
        this(name, type, "");
    }

    public String name;
    public String type;

    public Slot(String name, String typeName, String defaultValue) {
        this.name = name;
        this.type = typeName;
        this.defaultValue = defaultValue;
    }
}
