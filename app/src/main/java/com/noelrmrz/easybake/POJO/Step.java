package com.noelrmrz.easybake.POJO;

import com.google.gson.annotations.SerializedName;

public class Step {

    @SerializedName("id")
    private int mId;

    @SerializedName("shortDescription")
    private String mShortDescription;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("videoURL")
    private String mVideoUrl;

    @SerializedName("thumbnailURL")
    private String mThumbnailUrl;

    public Step(int id, String shortDescription, String description, String videoUrl,
                String thumbnailUrl) {
        mId = id;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoUrl = videoUrl;
        mThumbnailUrl = thumbnailUrl;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmShortDescription() {
        return mShortDescription;
    }

    public void setmShortDescription(String mShortDescription) {
        this.mShortDescription = mShortDescription;
    }

    public String getmVideoUrl() {
        return mVideoUrl;
    }

    public void setmVideoUrl(String mVideoUrl) {
        this.mVideoUrl = mVideoUrl;
    }

    public String getmThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setmThumbnailUrl(String mThumbnailUrl) {
        this.mThumbnailUrl = mThumbnailUrl;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
