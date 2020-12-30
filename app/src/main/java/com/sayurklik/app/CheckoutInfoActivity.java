package com.sayurklik.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.google.android.material.textview.MaterialTextView;
import com.sayurklik.app.helpers.IntentHelper;
import com.sayurklik.app.helpers.ProgressBarHelper;
import com.sayurklik.app.models.PaymentModel;

public class CheckoutInfoActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    ProgressBarHelper mPbh;
    WebView webView;
    MaterialTextView mtvOrderId, mtvPengiriman, mtvTotalBayar;
    //include
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_info);
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mtvOrderId = findViewById(R.id.mtv_order_id);
        mtvPengiriman = findViewById(R.id.mtv_pengiriman);
        mtvTotalBayar = findViewById(R.id.mtv_total);
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
        mPbh = findViewById(R.id.pbh);
        webView = findViewById(R.id.webView);
        String billingId = getIntent().getStringExtra(consts.BILLING_ID);
        if(billingId.contains("1")){
            retrievePayment();
        }
        retrievePayment();
        retrieveInvoice();
    }
    private void retrievePayment(){
        new PaymentModel.Get(this)
                .setProgressBar(mPbh)
                .showInto(recyclerView);
    }
    private void retrieveInvoice(){
        String orderId = getIntent().getStringExtra(consts.ORDER_ID);
        webView.loadUrl(consts.INVOICE + orderId);
    }

    @Override
    public void onBackPressed() {
        new IntentHelper(this).intentBackToParent();
    }

    @Override
    public void onClick(View v) {
        if(v == btnBack){
            new IntentHelper(CheckoutInfoActivity.this).intentBackToParent();
        }
    }
}
