package com.sayurklik.app.items;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductItem implements Parcelable {
    private String mId;
    private String mProduct_name;
    private String mProduct_unit;
    private String mProduct_description;
    private String mProduct_price;
    private String mProduct_stock;
    private String mProduct_thumbs;
    private String mProduct_image;
    private String mProduct_category;
    private String mProduct_tags;
    private String mUpload_by;
    private String mPUpload_date;

    public ProductItem(){
    }

    public static final Creator<ProductItem> CREATOR = new Creator<ProductItem>() {
        @Override
        public ProductItem createFromParcel(Parcel in) {
            return new ProductItem(in);
        }

        @Override
        public ProductItem[] newArray(int size) {
            return new ProductItem[size];
        }
    };

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmProduct_name() {
        return mProduct_name;
    }

    public void setmProduct_name(String mProduct_name) {
        this.mProduct_name = mProduct_name;
    }

    public String getmProduct_unit() {
        return mProduct_unit;
    }

    public void setmProduct_unit(String mProduct_unit) {
        this.mProduct_unit = mProduct_unit;
    }

    public String getmProduct_description() {
        return mProduct_description;
    }

    public void setmProduct_description(String mProduct_description) {
        this.mProduct_description = mProduct_description;
    }

    public String getmProduct_price() {
        return mProduct_price;
    }

    public void setmProduct_price(String mProduct_price) {
        this.mProduct_price = mProduct_price;
    }

    public String getmProduct_stock() {
        return mProduct_stock;
    }

    public void setmProduct_stock(String mProduct_stock) {
        this.mProduct_stock = mProduct_stock;
    }

    public String getmProduct_thumbs() {
        return mProduct_thumbs;
    }

    public void setmProduct_thumbs(String mProduct_thumbs) {
        this.mProduct_thumbs = mProduct_thumbs;
    }

    public String getmProduct_image() {
        return mProduct_image;
    }

    public void setmProduct_image(String mProduct_image) {
        this.mProduct_image = mProduct_image;
    }

    public String getmProduct_category() {
        return mProduct_category;
    }

    public void setmProduct_category(String mProduct_category) {
        this.mProduct_category = mProduct_category;
    }

    public String getmProduct_tags() {
        return mProduct_tags;
    }

    public void setmProduct_tags(String mProduct_tags) {
        this.mProduct_tags = mProduct_tags;
    }

    public String getmUpload_by() {
        return mUpload_by;
    }

    public void setmUpload_by(String mUpload_by) {
        this.mUpload_by = mUpload_by;
    }

    public String getmPUpload_date() {
        return mPUpload_date;
    }

    public void setmPUpload_date(String mPUpload_date) {
        this.mPUpload_date = mPUpload_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mProduct_name);
        dest.writeString(this.mProduct_unit);
        dest.writeString(this.mProduct_description);
        dest.writeString(this.mProduct_price);
        dest.writeString(this.mProduct_stock);
        dest.writeString(this.mProduct_thumbs);
        dest.writeString(this.mProduct_image);
        dest.writeString(this.mProduct_category);
        dest.writeString(this.mProduct_tags);
        dest.writeString(this.mUpload_by);
        dest.writeString(this.mPUpload_date);
    }

    protected ProductItem(Parcel in) {
        this.mId = in.readString();
        this.mProduct_name = in.readString();
        this.mProduct_unit = in.readString();
        this.mProduct_description = in.readString();
        this.mProduct_price = in.readString();
        this.mProduct_stock = in.readString();
        this.mProduct_thumbs = in.readString();
        this.mProduct_image = in.readString();
        this.mProduct_category = in.readString();
        this.mProduct_tags = in.readString();
        this.mUpload_by = in.readString();
        this.mPUpload_date = in.readString();
    }
    public static class set{
        ProductItem item;
        public set addObject(ProductItem item){
            this.item = item;
            return this;
        }
    }
}
