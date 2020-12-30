package com.sayurklik.app.adapters.feeds;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sayurklik.app.ProductDetailActivity;
import com.sayurklik.app.R;
import com.sayurklik.app.consts;
import com.sayurklik.app.helpers.SayurKlikHelper;
import com.sayurklik.app.items.ProductItem;
import com.sayurklik.app.helpers.IntentHelper;

import java.util.List;

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

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {

    private LayoutInflater inflanter;
    private Context context;
    private List<ProductItem> data;
    public FeedAdapter(Context context, List<ProductItem> data) {
        this.context = context;
        inflanter = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        MyViewHolder holder = null;
        view = inflanter.inflate(R.layout.item_product_feed, parent, false);
        holder = new MyViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ProductItem current = data.get(position);
        holder.title.setText(current.getmProduct_name());
        holder.price.setText(new SayurKlikHelper(context).rupiah(current.getmProduct_price()));
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
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageThumbs;
    TextView title, price;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_product_title);
            price = itemView.findViewById(R.id.text_product_price);
            imageThumbs = itemView.findViewById(R.id.image_product);
        }
    }
}