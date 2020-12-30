package com.sayurklik.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.sayurklik.app.adapters.OrderAdapter;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.items.OrderItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    OrderAdapter adapter;
    List<OrderItem> dataList = new ArrayList<>();
    AuthHelper authHelper;
    OrderItem.Status getStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        authHelper = new AuthHelper(this);
        adapter = new OrderAdapter(this, dataList);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        getStatus = getIntent().getParcelableExtra(consts.PARCELABLE);
        getOrder();
    }
    private void getOrder(){
        String orderStatus = getStatus.getStatusId();
        AndroidNetworking.get(consts.ORDER)
                .addHeaders(new AuthHelper(this).basicAuth())
                .addQueryParameter(consts.STATUS, orderStatus)
                .addQueryParameter(consts.USER_ID, authHelper.userdata(consts.ID))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray(consts.DATA);
                            for (int i = 0; i < array.length(); i++){
                                JSONObject object = array.getJSONObject(i);
                                OrderItem item = new OrderItem();
                                //item.setId(object.getString(consts.ID));
                                item.setOrderId(object.getString(consts.ORDER_ID));
                                item.setOrderDate(object.getString(consts.ORDER_DATE));
                                item.setAddress(object.getString(consts.ADDRESS));
                               // item.setBillingName(object.getString(consts.BILLING_NAME));
                               // item.setStatusName(object.getString(consts.STATUS_NAME));
                                item.setPhone(object.getString(consts.PHONE));
                                item.setName(object.getString(consts.NAME));
                                item.setStatus(object.getString(consts.STATUS));
                                dataList.add(item);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
