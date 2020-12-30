package com.sayurklik.app.models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.badge.BadgeDrawable;
import com.sayurklik.app.MainActivity;
import com.sayurklik.app.adapters.CartAdapter;
import com.sayurklik.app.consts;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.items.CartItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class CartModel {
    private Context context;
    public CartModel(Context context){
        this.context = context;
    }
    public void updateTotal(final String id, final String total){
        AndroidNetworking.put(consts.CART)
                .addHeaders(new AuthHelper(context).basicAuth())
                .addUrlEncodeFormBodyParameter(consts.ID, id)
                .addUrlEncodeFormBodyParameter(consts.TOTAL, total)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(!response.getBoolean("status")){
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
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
    public void addToCart(String productId, String userId, final BadgeDrawable badge){
        AndroidNetworking.post(consts.CART)
                .addHeaders(new AuthHelper(context).basicAuth())
                .addUrlEncodeFormBodyParameter(consts.PRODUCT_ID, productId)
                .addUrlEncodeFormBodyParameter(consts.USER_ID, userId)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString(consts.MESSAGE);
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            if (response.getBoolean(consts.STATUS)){
                                setCartBadge(badge);
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
    public void deleteCart(String id, final List<CartItem> data, final int position){
        AndroidNetworking.delete(consts.CART)
                .addHeaders(new AuthHelper(context).basicAuth())
                .addQueryParameter(consts.ID, id)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("status")){
                                data.remove(position);
                                new CartAdapter(context, data).notifyItemRemoved(position);
                            }
                            //Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
    public void setCartBadge(final BadgeDrawable badge){
        badge.setVisible(false);
        String userId = new AuthHelper(context).userdata(consts.ID);
        AndroidNetworking.get(consts.CART)
                .addHeaders(new AuthHelper(context).basicAuth())
                .setPriority(Priority.LOW)
                .addQueryParameter(consts.ID, userId)
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
