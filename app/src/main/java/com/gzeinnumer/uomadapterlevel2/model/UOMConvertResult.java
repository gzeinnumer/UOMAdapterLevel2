package com.gzeinnumer.uomadapterlevel2.model;

public class UOMConvertResult {
    public String name;
    public int newValue = 0;

    public UOMConvertResult(String name, int newValue) {
        this.name = name;
        this.newValue = newValue;
    }

    public void setNewValue(int newValue) {
        this.newValue = newValue;
    }

    @Override
    public String toString() {
        return name + " - " + newValue;
    }
}
