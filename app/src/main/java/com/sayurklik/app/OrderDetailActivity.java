package com.sayurklik.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sayurklik.app.items.OrderItem;

public class OrderDetailActivity extends AppCompatActivity {
    OrderItem orderItem;
    private TextView tvName, tvOrderStatus, tvStatusDescription, tvAddress, tvPhone;
    private LinearLayout lineInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        orderItem = getIntent().getParcelableExtra(consts.PARCELABLE);
        setTitle(orderItem.getOrderId());
        lineInfo = findViewById(R.id.line_info);
        tvName = findViewById(R.id.tv_name);
        tvOrderStatus = findViewById(R.id.tv_order_status);
        tvStatusDescription = findViewById(R.id.tv_status_description);
        tvAddress = findViewById(R.id.tv_address);
        tvPhone = findViewById(R.id.tv_phone);
        setData();
    }
    private void setData(){
        tvName.setText(orderItem.getName());
        tvOrderStatus.setText(orderItem.getStatusName());
        switch (Integer.parseInt(orderItem.getStatus())){
            case 1 :
                lineInfo.setBackgroundColor(getResources().getColor(R.color.colorSelesai));
                tvStatusDescription.setText("Orderan anda sudah "+ orderItem.getStatusName());
                break;
            case 2 :
                lineInfo.setBackgroundColor(getResources().getColor(R.color.colorPengantaran));
                tvStatusDescription.setText("Orderan anda sudah "+ orderItem.getStatusName());
                break;
            case 3 :
                lineInfo.setBackgroundColor(getResources().getColor(R.color.colorDiKonfirmasi));
                tvStatusDescription.setText("Orderan anda sudah "+ orderItem.getStatusName());
                break;
            case 4 :
                lineInfo.setBackgroundColor(getResources().getColor(R.color.colorPending));
                tvStatusDescription.setText("Menunggu konfirmasi.");
                break;
        }
        tvAddress.setText(orderItem.getAddress());
        tvPhone.setText(orderItem.getPhone().replaceFirst("0", "(+62) "));
    }
}
