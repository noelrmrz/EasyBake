package com.noelrmrz.easybake.POJO;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("ingredient")
    private String mName;

    @SerializedName("measure")
    private String mMeasure;

    @SerializedName("quantity")
    private double mQuantity;

    public Ingredient (String name, String measure, int quantity) {
        mName = name;
        mMeasure = measure;
        mQuantity = quantity;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmMeasure() {
        return mMeasure;
    }

    public void setmMeasure(String mMeasure) {
        this.mMeasure = mMeasure;
    }

    public double getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(double mQuantity) {
        this.mQuantity = mQuantity;
    }
}
