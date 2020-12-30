package com.sayurklik.app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sayurklik.app.MainActivity;
import com.sayurklik.app.ProductDetailActivity;
import com.sayurklik.app.R;
import com.sayurklik.app.consts;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.items.ProductItem;
import com.sayurklik.app.helpers.IntentHelper;
import com.sayurklik.app.models.CartModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/*
 * Copyright (c) 2020. by Fery Irawan
 * Contact Person :
 * facebook : https://facebook.com/fery21irawan
 * instagram : https://instagram.com/fery21irawan
 * Phone : (+62) 822 3406 8387
 * Whatsapp : (+62) 822 3406 8387
 * site : https://fery21irawan.my.id
 * Address : Jl. Desa Pamalian RT. 05 RW. 02 Pamalian Kec. Kota Besi
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private LayoutInflater inflanter;
    private Context context;
    private List<ProductItem> data;

    public ProductAdapter(Context context, List<ProductItem> data) {
        this.context = context;
        inflanter = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        MyViewHolder holder = null;
        view = inflanter.inflate(R.layout.item_product_grid_v2, parent, false);
        holder = new MyViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ProductItem current = data.get(position);
        holder.tvTitle.setText(current.getmProduct_name());
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
        DecimalFormat df = (DecimalFormat)nf;
        df.setGroupingUsed(true);
        df.setGroupingSize(3);
        String duit = df.format(Integer.parseInt(current.getmProduct_price()));
        holder.tvPrice.setText("Rp. "+duit+"/"+current.getmProduct_unit());
        Glide.with(context)
                .load(consts.IMAGE_URL + current.getmProduct_thumbs())
                .into(holder.imageThumbs);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentHelper intentHelper = new IntentHelper(context);
                intentHelper.intentForward(ProductDetailActivity.class, current);
            }
        });
        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartModel cartModel = new CartModel(context);
                AuthHelper authHelper = new AuthHelper(context);
                cartModel.addToCart(current.getmId(), authHelper.userdata(consts.ID), MainActivity.badgeCart);
            }
        });
    }

    @Override
    public int getItemCount() {
        /*if(data != null){
            return data.size();
        }else {
            return 0;
        }*/
        return data.size();
        //return data != null ? data.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageThumbs, imageAddToCart;
    TextView tvTitle, tvPrice, tvUnit;
    Button btnAddToCart;

        public MyViewHolder(View itemView) {
            super(itemView);
            /*tvTitle = itemView.findViewById(R.id.text_product_title);
            tvPrice = itemView.findViewById(R.id.text_product_price);*/
            tvTitle = itemView.findViewById(R.id.tv_product_name);
            tvPrice = itemView.findViewById(R.id.tv_product_price);
            imageThumbs = itemView.findViewById(R.id.image_product);
            tvUnit = itemView.findViewById(R.id.tv_product_unit);
            btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
            //imageAddToCart = itemView.findViewById(R.id.image_add_to_cart);
        }
    }
}