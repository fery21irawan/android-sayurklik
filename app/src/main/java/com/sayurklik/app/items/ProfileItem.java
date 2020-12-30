package com.sayurklik.app.items;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfileItem implements Parcelable {
    private int icon;
    private String title;
    private Class aClass;
    public ProfileItem(){

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

    protected ProfileItem(Parcel in) {
        this.icon = in.readInt();
        this.title = in.readString();
        this.aClass = (Class) in.readSerializable();
    }

    public static final Parcelable.Creator<ProfileItem> CREATOR = new Parcelable.Creator<ProfileItem>() {
        @Override
        public ProfileItem createFromParcel(Parcel source) {
            return new ProfileItem(source);
        }

        @Override
        public ProfileItem[] newArray(int size) {
            return new ProfileItem[size];
        }
    };
}
