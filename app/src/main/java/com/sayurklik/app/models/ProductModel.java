package com.sayurklik.app.models;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.sayurklik.app.adapters.ProductAdapter;
import com.sayurklik.app.consts;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.helpers.ProgressBarHelper;
import com.sayurklik.app.items.ProductItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductModel {

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
        // get product by id and retrieve in object
        public void getProduct(final ProductItem item, String value){
            AndroidNetworking.get(consts.PRODUCT)
                    .addHeaders(new AuthHelper(context).basicAuth())
                    .addQueryParameter(consts.ID, value)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                    JSONObject object = response.getJSONObject(consts.DATA);
                                    item.setmId(object.getString(consts.ID));
                                    item.setmProduct_name(object.getString(consts.PRODUCT_NAME));
                                    item.setmProduct_description(object.getString(consts.PRODUCT_DESC));
                                    item.setmProduct_price(object.getString(consts.PRODUCT_PRICE));
                                    item.setmProduct_thumbs(object.getString(consts.PRODUCT_THUMBS));
                                    item.setmProduct_image(object.getString(consts.PRODUCT_IMAGE));
                                    item.setmProduct_unit(object.getString(consts.PRODUCT_UNIT));
//                                    if(mPbh.isShown()){
//                                        mPbh.clearAnimation();
//                                        mPbh.setVisibility(View.GONE);
//                                    }
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
}
