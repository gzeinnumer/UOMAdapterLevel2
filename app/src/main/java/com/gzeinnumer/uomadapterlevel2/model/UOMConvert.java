package com.gzeinnumer.uomadapterlevel2.model;

public class UOMConvert {
    public int buy;
    public int inPcs;
    public String name;
    public int newValue = 0;

    public UOMConvert(int buy, int inPcs, String name) {
        this.buy = buy;
        this.inPcs = inPcs;
        this.name = name;
    }

    public void setNewValue(int newValue) {
        this.newValue = newValue;
    }
}
