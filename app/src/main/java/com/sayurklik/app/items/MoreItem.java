package com.sayurklik.app.items;

import android.os.Parcel;
import android.os.Parcelable;

public class MoreItem implements Parcelable {
    private int icon;
    private String title;
    private Class aClass;
    public MoreItem(){

    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.icon);
        dest.writeString(this.title);
        dest.writeSerializable(this.aClass);
    }

    protected MoreItem(Parcel in) {
        this.icon = in.readInt();
        this.title = in.readString();
        this.aClass = (Class) in.readSerializable();
    }

    public static final Creator<MoreItem> CREATOR = new Creator<MoreItem>() {
        @Override
        public MoreItem createFromParcel(Parcel source) {
            return new MoreItem(source);
        }

        @Override
        public MoreItem[] newArray(int size) {
            return new MoreItem[size];
        }
    };
}
