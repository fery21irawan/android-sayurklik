package com.sayurklik.app.items;

import android.os.Parcel;

public class CartItem extends ProductItem {
    private String mId;
    private String mOrderId;
    private String mProductId;
    private String mTotal;
    private String mUserId;
    public CartItem(){

    }

    @Override
    public String getmId() {
        return mId;
    }

    @Override
    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmOrderId() {
        return mOrderId;
    }

    public void setmOrderId(String mOrderId) {
        this.mOrderId = mOrderId;
    }

    public String getmProductId() {
        return mProductId;
    }

    public void setmProductId(String mProductId) {
        this.mProductId = mProductId;
    }

    public String getmTotal() {
        return mTotal;
    }

    public void setmTotal(String mTotal) {
        this.mTotal = mTotal;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mId);
        dest.writeString(this.mOrderId);
        dest.writeString(this.mProductId);
        dest.writeString(this.mTotal);
        dest.writeString(this.mUserId);
    }

    protected CartItem(Parcel in) {
        super(in);
        this.mId = in.readString();
        this.mOrderId = in.readString();
        this.mProductId = in.readString();
        this.mTotal = in.readString();
        this.mUserId = in.readString();
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel source) {
            return new CartItem(source);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };
}
