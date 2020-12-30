package com.sayurklik.app.models;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.sayurklik.app.adapters.BillingAdapter;
import com.sayurklik.app.consts;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.helpers.ProgressBarHelper;
import com.sayurklik.app.items.BillingItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BillingModel {

    public static class Get{
        private Context context;
        private List<BillingItem> itemList = new ArrayList<>();
        private BillingAdapter adapter;
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
            adapter = new BillingAdapter(context, itemList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            if(!key.equalsIgnoreCase("")){
                getBilling(key, value);
            }else{
                getBilling();
            }
        }

        private void getBilling(){
            itemList.clear();
            //make request or get data from sqlite in below
            AndroidNetworking.get(consts.BILLING)
                    .addHeaders(new AuthHelper(context).basicAuth())
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray array = response.getJSONArray("data");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    BillingItem item = new BillingItem();
                                    item.setBillingId(object.getString(consts.ID));
                                    item.setBillingName(object.getString(consts.BILLING_NAME));
                                    itemList.add(item);
                                    //do
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
        private void getBilling(String key, String value){
            this.key = key;
            this.value = value;
            itemList.clear();
            //make request or get data from sqlite with parameter in below
        }
    }
}
