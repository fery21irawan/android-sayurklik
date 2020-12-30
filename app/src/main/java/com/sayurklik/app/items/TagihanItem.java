package com.sayurklik.app.items;

import android.os.Parcel;
import android.os.Parcelable;

public class TagihanItem implements Parcelable {
    private String id;
    private String orderId;
    private String imagePayment;
    private String confirmDate;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getImagePayment() {
        return imagePayment;
    }

    public void setImagePayment(String imagePayment) {
        this.imagePayment = imagePayment;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TagihanItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.orderId);
        dest.writeString(this.imagePayment);
        dest.writeString(this.confirmDate);
        dest.writeString(this.status);
    }

    protected TagihanItem(Parcel in) {
        this.id = in.readString();
        this.orderId = in.readString();
        this.imagePayment = in.readString();
        this.confirmDate = in.readString();
        this.status = in.readString();
    }

    public static final Creator<TagihanItem> CREATOR = new Creator<TagihanItem>() {
        @Override
        public TagihanItem createFromParcel(Parcel source) {
            return new TagihanItem(source);
        }

        @Override
        public TagihanItem[] newArray(int size) {
            return new TagihanItem[size];
        }
    };
}
