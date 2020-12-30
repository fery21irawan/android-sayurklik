package com.sayurklik.app.items;

import android.os.Parcel;
import android.os.Parcelable;

public class FeedItem extends ProductItem implements Parcelable {
    private String mId;
    private String mCategoryName;
    private String mData;

    public FeedItem(){

    }

    @Override
    public String getmId() {
        return mId;
    }

    @Override
    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }

    public void setmCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }

    public String getmData() {
        return mData;
    }

    public void setmData(String mData) {
        this.mData = mData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mId);
        dest.writeString(this.mCategoryName);
        dest.writeString(this.mData);
    }

    protected FeedItem(Parcel in) {
        super(in);
        this.mId = in.readString();
        this.mCategoryName = in.readString();
        this.mData = in.readString();
    }

    public static final Creator<FeedItem> CREATOR = new Creator<FeedItem>() {
        @Override
        public FeedItem createFromParcel(Parcel source) {
            return new FeedItem(source);
        }

        @Override
        public FeedItem[] newArray(int size) {
            return new FeedItem[size];
        }
    };
}
