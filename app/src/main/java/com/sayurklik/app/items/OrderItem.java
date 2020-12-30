package com.sayurklik.app.items;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable {
   private String id;
   private String orderId;
   private String name;
   private String address;
   private String phone;
   private String message;
   private String orderDate;
   private String status;
   private String statusName;
   private String billingName;
   public static class Status implements Parcelable {
       private String statusId;
       private String statusName;
       private String totalRows;
       public Status(){}

       public String getStatusId() {
           return statusId;
       }

       public void setStatusId(String statusId) {
           this.statusId = statusId;
       }

       public String getStatusName() {
           return statusName;
       }

       public void setStatusName(String statusName) {
           this.statusName = statusName;
       }

       public String getTotalRows() {
           return totalRows;
       }

       public void setTotalRows(String totalRows) {
           this.totalRows = totalRows;
       }

       @Override
       public int describeContents() {
           return 0;
       }

       @Override
       public void writeToParcel(Parcel dest, int flags) {
           dest.writeString(this.statusId);
           dest.writeString(this.statusName);
           dest.writeString(this.totalRows);
       }

       protected Status(Parcel in) {
           this.statusId = in.readString();
           this.statusName = in.readString();
           this.totalRows = in.readString();
       }

       public static final Creator<Status> CREATOR = new Creator<Status>() {
           @Override
           public Status createFromParcel(Parcel source) {
               return new Status(source);
           }

           @Override
           public Status[] newArray(int size) {
               return new Status[size];
           }
       };
   }
   public OrderItem(){}

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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
        dest.writeString(this.id);
        dest.writeString(this.orderId);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.phone);
        dest.writeString(this.message);
        dest.writeString(this.orderDate);
        dest.writeString(this.status);
        dest.writeString(this.statusName);
        dest.writeString(this.billingName);
    }

    protected OrderItem(Parcel in) {
        this.id = in.readString();
        this.orderId = in.readString();
        this.name = in.readString();
        this.address = in.readString();
        this.phone = in.readString();
        this.message = in.readString();
        this.orderDate = in.readString();
        this.status = in.readString();
        this.statusName = in.readString();
        this.billingName = in.readString();
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel source) {
            return new OrderItem(source);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };
}
