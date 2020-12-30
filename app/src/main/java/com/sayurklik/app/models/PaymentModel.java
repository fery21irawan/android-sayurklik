package com.sayurklik.app.models;


import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.sayurklik.app.adapters.PaymentAdapter;
import com.sayurklik.app.adapters.ProductAdapter;
import com.sayurklik.app.consts;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.helpers.ProgressBarHelper;
import com.sayurklik.app.items.PaymentItem;
import com.sayurklik.app.items.ProductItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.androidnetworking.AndroidNetworking.get;

public class PaymentModel {
    public static class Get{
        private Context context;
        private List<PaymentItem> itemList = new ArrayList<>();
        private PaymentAdapter adapter;
        private ProgressBarHelper mPbh;

        public Get(Context context){
            this.context = context;
        }
        public Get setProgressBar(ProgressBarHelper pbh){
            this.mPbh = pbh;
            return this;
        }
        public void showInto(RecyclerView recyclerView){
            adapter = new PaymentAdapter(context, itemList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            getPayment();
        }

        private void getPayment(){
            itemList.clear();
            AndroidNetworking.get(consts.PAYMENT)
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
                                    PaymentItem item = new PaymentItem();
                                    item.setId(object.getString(consts.ID));
                                    item.setBankName(object.getString("bank_name"));
                                    item.setBankRekening(object.getString("bank_rekening"));
                                    item.setAn(object.getString("an"));
                                    itemList.add(item);
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
            //make request or get data from sqlite in below
        }
    }
}
