package com.sayurklik.app;

import android.content.Intent;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.helpers.SayurKlikHelper;
import com.sayurklik.app.items.ProductItem;
import com.sayurklik.app.models.CartModel;
import com.sayurklik.app.models.ProductModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {
    ProductItem data;
    Button btnAddToCart;
   // ImageListener imageListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        data = intent.getParcelableExtra(consts.PARCELABLE);
        setTitle(data.getmProduct_name());
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartModel cartModel = new CartModel(ProductDetailActivity.this);
                AuthHelper authHelper = new AuthHelper(ProductDetailActivity.this);
                cartModel.addToCart(data.getmId(), authHelper.userdata(consts.ID), MainActivity.badgeCart);
            }
        });
        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                new SayurKlikHelper(ProductDetailActivity.this).callToWhatsApp("+6282243200046");
            }
        });
        set_image_product();
        set_product_information();
    }
    private void set_image_product(){
        ArrayList<SlideModel> imageList = new ArrayList<>();
        ImageSlider imageSlider;
        String[] explode = data.getmProduct_image().split(",");
        for (String filename : explode) {
            imageList.add(new SlideModel(consts.IMAGE_URL + filename, null));
        }
        imageSlider = findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP);
        imageSlider.stopSliding();
    }
    private void set_product_information(){
       MaterialTextView mTextTitle, mTextPrice;
        mTextTitle = findViewById(R.id.text_product_title);
        mTextPrice = findViewById(R.id.text_product_price);
        mTextTitle.setText(data.getmProduct_name());
        mTextPrice.setText("Rp. "+data.getmProduct_price());
    }

}
