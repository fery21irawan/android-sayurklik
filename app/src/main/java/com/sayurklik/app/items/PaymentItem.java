package com.sayurklik.app.items;

import android.os.Parcel;
import android.os.Parcelable;

public class PaymentItem implements Parcelable {

    private String id;
    private String bankName;
    private String bankRekening;
    private String an;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankRekening() {
        return bankRekening;
    }

    public void setBankRekening(String bankRekening) {
        this.bankRekening = bankRekening;
    }

    public String getAn() {
        return an;
    }

    public void setAn(String an) {
        this.an = an;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.bankName);
        dest.writeString(this.bankRekening);
        dest.writeString(this.an);
    }

    public PaymentItem() {
    }

    protected PaymentItem(Parcel in) {
        this.id = in.readString();
        this.bankName = in.readString();
        this.bankRekening = in.readString();
        this.an = in.readString();
    }

    public static final Parcelable.Creator<PaymentItem> CREATOR = new Parcelable.Creator<PaymentItem>() {
        @Override
        public PaymentItem createFromParcel(Parcel source) {
            return new PaymentItem(source);
        }

        @Override
        public PaymentItem[] newArray(int size) {
            return new PaymentItem[size];
        }
    };
}
