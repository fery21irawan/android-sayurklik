package com.sayurklik.app.items;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryItem implements Parcelable {
    private String mId;
    private String mCategoryName;
    public CategoryItem(){

    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }

    public void setmCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mCategoryName);
    }

    protected CategoryItem(Parcel in) {
        this.mId = in.readString();
        this.mCategoryName = in.readString();
    }

    public static final Parcelable.Creator<CategoryItem> CREATOR = new Parcelable.Creator<CategoryItem>() {
        @Override
        public CategoryItem createFromParcel(Parcel source) {
            return new CategoryItem(source);
        }

        @Override
        public CategoryItem[] newArray(int size) {
            return new CategoryItem[size];
        }
    };
}
