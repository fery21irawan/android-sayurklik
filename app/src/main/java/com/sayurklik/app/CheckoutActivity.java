package com.sayurklik.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.textview.MaterialTextView;
import com.sayurklik.app.adapters.BillingAdapter;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.helpers.IntentHelper;
import com.sayurklik.app.helpers.ProgressBarHelper;
import com.sayurklik.app.helpers.SayurKlikHelper;
import com.sayurklik.app.items.BillingItem;
import com.sayurklik.app.items.CartItem;
import com.sayurklik.app.models.BillingModel;
import com.sayurklik.app.models.CheckoutModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {
    private MaterialTextView mtvSubtotal, mtvPengiriman, mtvTotal;
    AuthHelper authHelper;
    EditText nama,alamat,noTelp,pesan;
    Button btnCheckOut;
    private int totalPembelian = 0;
    private int pengiriman = 0;
    private int total = 0;
    ArrayList<CartItem> dataList;

    //untuk mengambil metode pembayaran yang dipilih
    public static BillingItem billingItem = new BillingItem();
    private String id;

    RecyclerView recyclerView;
    private ProgressBarHelper mPbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        authHelper = new AuthHelper(this);
        recyclerView = findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        mPbh = findViewById(R.id.pbh);
        nama = findViewById(R.id.tf_nama);
        alamat = findViewById(R.id.tf_alamat);
        noTelp = findViewById(R.id.tf_no_telp);
        pesan = findViewById(R.id.tf_pesan);
        mtvSubtotal = findViewById(R.id.mtv_subtotal);
        mtvPengiriman = findViewById(R.id.mtv_pengiriman);
        mtvTotal = findViewById(R.id.mtv_total);
        btnCheckOut = findViewById(R.id.button_checkout);

        btnCheckOut.setOnClickListener(this);

        nama.setText(authHelper.userdata(consts.FULLNAME));
        alamat.setText(authHelper.userdata(consts.ADDRESS));
        noTelp.setText(authHelper.userdata(consts.PHONE));
        dataList = new ArrayList<>();
        //hitung Total Pembelian
        hitungTotal();
        //atur metode pembayaran
        setBilling();
        getTransaksiId();
        Log.d("productCheck", productData());
    }
    private void hitungTotal(){
        dataList = getIntent().getParcelableArrayListExtra(consts.PARCELABLE);
        for (int i = 0; i < dataList.size(); i++){
            int perItem = Integer.parseInt(dataList.get(i).getmTotal())*Integer.parseInt(dataList.get(i).getmProduct_price());
            totalPembelian += perItem;
        }
        pengiriman = 12000;
        total = totalPembelian + pengiriman;
        mtvSubtotal.setText(new SayurKlikHelper(this).rupiah(totalPembelian));
        mtvPengiriman.setText(new SayurKlikHelper(this).rupiah(pengiriman));
        mtvTotal.setText(new SayurKlikHelper(this).rupiah(total));
    }
    private void setBilling(){
        new BillingModel.Get(this)
                .setProgressBar(mPbh)
                .showInto(recyclerView);
    }
    private String productData(){
        String result = null;
        try {
            JSONObject object = new JSONObject();
            JSONArray array = new JSONArray();
            for (int i = 0; i < dataList.size(); i++){
                int perItem = Integer.parseInt(dataList.get(i).getmTotal())*Integer.parseInt(dataList.get(i).getmProduct_price());
                JSONObject objCartId = new JSONObject();
                objCartId.put(consts.PRODUCT_NAME, dataList.get(i).getmProduct_name());
                objCartId.put("total_order", Integer.parseInt(dataList.get(i).getmTotal()));
                objCartId.put(consts.PRODUCT_UNIT, dataList.get(i).getmProduct_unit());
                objCartId.put(consts.PRODUCT_PRICE, Integer.parseInt(dataList.get(i).getmProduct_price()));
                objCartId.put("jumlah", perItem);
                array.put(objCartId);
            }
            object.put("sub_total", totalPembelian);
            object.put("pengantaran", pengiriman);
            object.put("products", array);
            result = object.toString();
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void getTransaksiId(){
        String userId = new AuthHelper(this).userdata(consts.ID);
        AndroidNetworking.get(consts.ORDER + "/idtransaksi")
                .addHeaders(new AuthHelper(this).basicAuth())
                .addQueryParameter("user_id", userId)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            id = response.getString("id");
                            setTitle("#" + id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
    private String getCartId(){
        String result = null;
        try {
            JSONObject object = new JSONObject();
            JSONArray array = new JSONArray();
            for (int i = 0; i < dataList.size(); i++){
                JSONObject objCartId = new JSONObject();
                objCartId.put(consts.CART_ID, dataList.get(i).getmId());
                array.put(objCartId);
            }
            object.put(consts.DATA, array);
            result = object.toString();
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public void onClick(View v) {
        if (v == btnCheckOut){
            if(!BillingAdapter.isCheckedBilling()){
                Toast.makeText(this, "Pilih Metode Pembayaran", Toast.LENGTH_SHORT).show();
            }else{
                Map<String, String> data = new HashMap<>();
                data.put(consts.CART_ID, getCartId());
                data.put(consts.ORDER_ID, id);
                data.put("total_bayar", String.valueOf(total));
                data.put("product_data", productData());
                data.put(consts.BILLING_ID, billingItem.getBillingId());
                data.put(consts.NAME, nama.getText().toString());
                data.put(consts.ADDRESS, alamat.getText().toString());
                data.put(consts.PHONE, noTelp.getText().toString());
                data.put(consts.MESSAGE, pesan.getText().toString());
                data.put(consts.USER_ID, new AuthHelper(this).userdata(consts.ID));

                new CheckoutModel.Post(this)
                        .setUrlEncodeFormBodyParameter(data)
                        .postCheckOut();
            }
        }
    }
}
