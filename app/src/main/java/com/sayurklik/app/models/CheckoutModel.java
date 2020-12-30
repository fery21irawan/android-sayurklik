package com.sayurklik.app.models;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.sayurklik.app.CheckoutActivity;
import com.sayurklik.app.CheckoutInfoActivity;
import com.sayurklik.app.adapters.ProductAdapter;
import com.sayurklik.app.consts;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.helpers.ProgressBarHelper;
import com.sayurklik.app.items.ProductItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutModel {

  //  public ProductModel(){ }

    public static class Get{
        private Context context;
        private List<ProductItem> itemList = new ArrayList<>();
        private ProductAdapter adapter;
        private ProgressBarHelper mPbh;
        //query parameter
        private String key = "";
        private String value = "";
        //pagination
        private int start;
        private int limit;

        public Get(Context context){
            this.context = context;
        }
        public Get setQueryParameter(String key, String value){
            this.key = key;
            this.value = value;
            return this;
        }
        public Get setProgressBar(ProgressBarHelper pbh){
            this.mPbh = pbh;
            return this;
        }
        public void showInto(RecyclerView recyclerView){
            adapter = new ProductAdapter(context, itemList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            if(!key.equalsIgnoreCase("")){
                getProduct(key, value);
            }else{
                getProduct();
            }
        }

        private void getProduct(){
            itemList.clear();
            AndroidNetworking.get(consts.PRODUCT)
                    .addHeaders(new AuthHelper(context).basicAuth())
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray array = response.getJSONArray(consts.DATA);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    ProductItem data = new ProductItem();
                                    data.setmId(object.getString(consts.ID));
                                    data.setmProduct_name(object.getString(consts.PRODUCT_NAME));
                                    data.setmProduct_description(object.getString(consts.PRODUCT_DESC));
                                    data.setmProduct_price(object.getString(consts.PRODUCT_PRICE));
                                    data.setmProduct_thumbs(object.getString(consts.PRODUCT_THUMBS));
                                    data.setmProduct_image(object.getString(consts.PRODUCT_IMAGE));
                                    data.setmProduct_unit(object.getString(consts.PRODUCT_UNIT));
                                    itemList.add(data);
                                    if(mPbh.isShown()){
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
        }
        private void getProduct(String key, String value){
            this.key = key;
            this.value = value;
            itemList.clear();
            AndroidNetworking.get(consts.PRODUCT)
                    .addHeaders(new AuthHelper(context).basicAuth())
                    .addQueryParameter(key, value)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray array = response.getJSONArray(consts.DATA);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    ProductItem data = new ProductItem();
                                    data.setmId(object.getString(consts.ID));
                                    data.setmProduct_name(object.getString(consts.PRODUCT_NAME));
                                    data.setmProduct_description(object.getString(consts.PRODUCT_DESC));
                                    data.setmProduct_price(object.getString(consts.PRODUCT_PRICE));
                                    data.setmProduct_thumbs(object.getString(consts.PRODUCT_THUMBS));
                                    data.setmProduct_image(object.getString(consts.PRODUCT_IMAGE));
                                    data.setmProduct_unit(object.getString(consts.PRODUCT_UNIT));
                                    itemList.add(data);
                                    if(mPbh.isShown()){
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
        }
    }
    public static class Post{
        private Context context;
        Map<String, String> bodyParameter;
        public Post(Context context){
            this.context = context;
        }
        public Post setUrlEncodeFormBodyParameter(Map<String, String> bodyParameter){
            this.bodyParameter = bodyParameter;
            return this;
        }
        public void postCheckOut(){
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("Memproses Data, silahkan tunggu ...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
            AndroidNetworking.post(consts.ORDER)
                    .addHeaders(new AuthHelper(context).basicAuth())
                    .addUrlEncodeFormBodyParameter(bodyParameter)
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                boolean status = response.getBoolean(consts.STATUS);
                                Log.d("Checkout", "status "+status);
                                if(status){
                                   // Toast.makeText(context, response.getString(consts.MESSAGE), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    String orderId = response.getString(consts.ORDER_ID);
                                    String billingId = response.getString("total_billing");
                                    Intent intent = new Intent(context, CheckoutInfoActivity.class);
                                    intent.putExtra(consts.BILLING_ID, billingId);
                                    intent.putExtra(consts.ORDER_ID, orderId);
                                    context.startActivity(intent);
                                   // ((Activity)context).finish();
                                }else{
                                    Toast.makeText(context, response.getString(consts.MESSAGE), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            if (dialog.isShowing()){
                                dialog.dismiss();
                            }
                            Toast.makeText(context, anError.getErrorCode(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
