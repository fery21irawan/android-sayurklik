package com.sayurklik.app.items;

import android.os.Parcel;
import android.os.Parcelable;

public class BillingItem implements Parcelable {

    private String billingId;
    private String billingName;

    public String getBillingId() {
        return billingId;
    }

    public void setBillingId(String billingId) {
        this.billingId = billingId;
    }

    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.billingId);
        dest.writeString(this.billingName);
    }

    public BillingItem() {
    }

    protected BillingItem(Parcel in) {
        this.billingId = in.readString();
        this.billingName = in.readString();
    }

    public static final Parcelable.Creator<BillingItem> CREATOR = new Parcelable.Creator<BillingItem>() {
        @Override
        public BillingItem createFromParcel(Parcel source) {
            return new BillingItem(source);
        }

        @Override
        public BillingItem[] newArray(int size) {
            return new BillingItem[size];
        }
    };
}
