package com.sayurklik.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.sayurklik.app.adapters.CartAdapter;
import com.sayurklik.app.helpers.SayurKlikHelper;
import com.sayurklik.app.items.CartItem;
import com.sayurklik.app.helpers.AuthHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton mButtonCheckout;
    AuthHelper authHelper;
    static List<CartItem> dataList = new ArrayList<>();
    CartAdapter adapter;
    RecyclerView recyclerView;
    public static TextView tvTotalItem;
    public static int grandTotalItem, grandPengiriman;
    CardView cardTotalItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        authHelper = new AuthHelper(this);
        adapter = new CartAdapter(this, dataList);
        recyclerView = findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        tvTotalItem = findViewById(R.id.tv_total_item);
        cardTotalItem = findViewById(R.id.card_total);
        mButtonCheckout = findViewById(R.id.button_checkout);
        mButtonCheckout.setOnClickListener(this);
        if(dataList.size() < 1){
            cardTotalItem.setVisibility(View.GONE);
            mButtonCheckout.setVisibility(View.GONE);
        }
        get();
    }
    private void calculateTotal(){
        grandTotalItem = 0;
        for (int i = 0; i < dataList.size(); i++) {
            int perItem = Integer.parseInt(dataList.get(i).getmTotal())*Integer.parseInt(dataList.get(i).getmProduct_price());
            grandTotalItem += perItem;
        }
        tvTotalItem.setText(new SayurKlikHelper(this).rupiah(grandTotalItem));
        grandPengiriman = 12000;
    }
    public static void updateTotal(){
        grandTotalItem = 0;
        for (int i = 0; i < dataList.size(); i++) {
            int perItem = Integer.parseInt(dataList.get(i).getmTotal())*Integer.parseInt(dataList.get(i).getmProduct_price());
            grandTotalItem += perItem;
        }
        grandPengiriman = 12000;
    }
    public void get(){
        dataList.clear();
        AndroidNetworking.get(consts.CART)
                .addHeaders(new AuthHelper(this).basicAuth())
                .addQueryParameter(consts.ID, authHelper.userdata(consts.ID))
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray(consts.DATA);
                            for (int i = 0; i < array.length(); i++){
                                JSONObject object = array.getJSONObject(i);
                                CartItem data = new CartItem();
                                data.setmId(object.getString(consts.CART_ID));
                                data.setmTotal(object.getString(consts.TOTAL));
                                data.setmProductId(object.getString(consts.PRODUCT_ID));
                                data.setmProduct_name(object.getString(consts.PRODUCT_NAME));
                                data.setmProduct_description(object.getString(consts.PRODUCT_DESC));
                                data.setmProduct_price(object.getString(consts.PRODUCT_PRICE));
                                data.setmProduct_unit(object.getString(consts.PRODUCT_UNIT));
                                data.setmProduct_thumbs(object.getString(consts.PRODUCT_THUMBS));
                                dataList.add(data);
                                mButtonCheckout.setVisibility(View.VISIBLE);
                                cardTotalItem.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        calculateTotal();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == mButtonCheckout){
            ArrayList<CartItem> myItem = new ArrayList<>(dataList);
            Intent intent = new Intent(this, CheckoutActivity.class);
            intent.putParcelableArrayListExtra(consts.PARCELABLE, myItem);
            startActivity(intent);
        }
    }
}
