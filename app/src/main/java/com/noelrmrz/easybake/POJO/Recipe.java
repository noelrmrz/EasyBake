package com.noelrmrz.easybake.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Recipe {
    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("ingredients")
    private ArrayList<Ingredient> mIngredients;

    @SerializedName("steps")
    private ArrayList<Step> mSteps;

    @SerializedName("image")
    private String mImageUrl;

    @SerializedName("servings")
    private int mServings;

    public Recipe (int id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps,
                   String imageUrl, int servings) {
        mId = id;
        mName = name;
        mIngredients = ingredients;
        mSteps = steps;
        mImageUrl = imageUrl;
        mServings = servings;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public ArrayList<Ingredient> getmIngredients() {
        return mIngredients;
    }

    public void setmIngredients(ArrayList<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public ArrayList<Step> getmSteps() {
        return mSteps;
    }

    public void setmSteps(ArrayList<Step> mSteps) {
        this.mSteps = mSteps;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public int getmServings() {
        return mServings;
    }

    public void setmServings(int mServings) {
        this.mServings = mServings;
    }
}
