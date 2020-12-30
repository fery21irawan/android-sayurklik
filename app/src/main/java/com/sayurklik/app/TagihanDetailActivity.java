package com.sayurklik.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;

import com.sayurklik.app.items.TagihanItem;

public class TagihanDetailActivity extends AppCompatActivity {
    Button btnBayarSekarang;
    WebView webView;
    TagihanItem tagihanItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagihan_detail);
        tagihanItem = getIntent().getParcelableExtra(consts.PARCELABLE);
        btnBayarSekarang = findViewById(R.id.btn_bayar_sekarang);
        webView = findViewById(R.id.webView);
        loadInvoice();
    }

    private void loadInvoice() {
        String orderId = tagihanItem.getOrderId();
        webView.loadUrl(consts.INVOICE + orderId);
    }
}
