package com.sayurklik.app.models;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.badge.BadgeDrawable;
import com.sayurklik.app.adapters.OrderAdapter;
import com.sayurklik.app.consts;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.helpers.ProgressBarHelper;
import com.sayurklik.app.helpers.SayurKlikHelper;
import com.sayurklik.app.items.BillingItem;
import com.sayurklik.app.items.OrderItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    private Context context;
    public OrderModel(Context context){
        this.context = context;
    }
    public static class Get {
        private Context context;
        private List<OrderItem.Status> itemList = new ArrayList<>();
        private OrderAdapter.Status adapter;
        private ProgressBarHelper mPbh;
        //query parameter
        private String key = "";
        private String value = "";
        //pagination
        private int start;
        private int limit;

        public Get(Context context) {
            this.context = context;
        }

        public Get setProgressBar(ProgressBarHelper pbh) {
            this.mPbh = pbh;
            return this;
        }

        public Get setQueryParameter(String key, String value) {
            this.key = key;
            this.value = value;
            return this;
        }

        public void showInto(RecyclerView recyclerView) {
            adapter = new OrderAdapter.Status(context, itemList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            getOrder();
        }

        private void getOrder() {
            itemList.clear();
            AndroidNetworking.get(consts.ORDER)
                    .addHeaders(new AuthHelper(context).basicAuth())
                    //.addQueryParameter(key, value)
                    .addQueryParameter(consts.USER_ID, new AuthHelper(context).userdata(consts.ID))
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Order", response.toString());
                            try {
                                JSONArray array = response.getJSONArray("data");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    //do
                                    OrderItem.Status item = new OrderItem.Status();
                                    item.setStatusId(object.getString("status_id"));
                                    item.setStatusName(object.getString("status_name"));
                                    item.setTotalRows(object.getString("total_rows"));
                                    //do this
                                    itemList.add(item);
                                    if (mPbh.isShown()) {
                                        mPbh.clearAnimation();
                                        mPbh.setVisibility(View.GONE);
                                    }
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
            adapter.notifyDataSetChanged();
        }
    }
    public void showTotalOrderInto(final TextView textView){
        AndroidNetworking.get(consts.ORDER + "/total_order")
                .addHeaders(new AuthHelper(context).basicAuth())
                .addQueryParameter(consts.USER_ID, new AuthHelper(context).userdata(consts.ID))
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String total = new SayurKlikHelper(context).rupiah(response.getString("total_order"));
                            textView.setText(total);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
    public void setTagihanBadge(final BadgeDrawable badge){
        badge.setVisible(false);
        String userId = new AuthHelper(context).userdata(consts.ID);
        AndroidNetworking.get(consts.TAGIHAN)
                .addHeaders(new AuthHelper(context).basicAuth())
                .setPriority(Priority.LOW)
                .addQueryParameter(consts.USER_ID, userId)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int totalRow = response.getInt("total_row");
                            if(totalRow > 0){
                                badge.setVisible(true);
                                badge.setNumber(totalRow);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}