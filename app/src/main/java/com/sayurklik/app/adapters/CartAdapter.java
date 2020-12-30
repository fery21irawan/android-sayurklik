package com.sayurklik.app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sayurklik.app.CartActivity;
import com.sayurklik.app.ProductDetailActivity;
import com.sayurklik.app.R;
import com.sayurklik.app.consts;
import com.sayurklik.app.helpers.IntentHelper;
import com.sayurklik.app.items.CartItem;
import com.sayurklik.app.models.CartModel;

import java.util.ArrayList;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private LayoutInflater inflanter;
    private Context context;
    private List<CartItem> data;

    public CartAdapter(Context context, List<CartItem> data) {
        this.context = context;
        inflanter = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = inflanter.inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public int getItemViewType(int position){
        return position;
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final CartItem current = data.get(position);
        final String id = current.getmId();
        final CartModel cartModel = new CartModel(context);
        holder.product_title.setText(current.getmProduct_name());
        holder.product_desc.setText(current.getmProduct_description());
        holder.product_price.setText(current.getmProduct_price());
        holder.total.setText(current.getmTotal());
        Glide.with(context)
                .load(consts.IMAGE_URL + current.getmProduct_thumbs())
                .into(holder.product_image);
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartModel.deleteCart(id, data, position);
                //notifyItemRemoved(position);
            }
        });
        int total_buy = Integer.parseInt(holder.total.getText().toString());
        if(total_buy < 2){
            holder.decrese.setVisibility(View.GONE);
        }else{
            holder.decrese.setVisibility(View.VISIBLE);
        }
       // int total_buy = Integer.parseInt(holder.tvTotal.getText().toString());
        holder.increse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = 0;
                try{
                    //val = Integer.parseInt(holder.total.getText().toString());
                    val = Integer.parseInt(current.getmTotal());
                }catch (Exception e){
                    val = 0;
                }
                val += 1;
                current.setmTotal(String.valueOf(val));
                holder.total.setText(current.getmTotal());
                holder.decrese.setVisibility(View.VISIBLE);
                cartModel.updateTotal(id, current.getmTotal());
                notifyItemChanged(position);
                CartActivity.updateTotal();
            }
        });
        holder.decrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int val = 0;
                try{
                    val = Integer.parseInt(holder.total.getText().toString());
                }catch (Exception e){
                    val = 0;
                }
                if(val > 0) val--;
                if(val < 2){
                    holder.decrese.setVisibility(View.GONE);
                }
                current.setmTotal(String.valueOf(val));
                holder.total.setText(current.getmTotal());
                cartModel.updateTotal(id, current.getmTotal());
                notifyItemChanged(position);
                CartActivity.updateTotal();
            }
        });
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

    TextView product_title, product_desc, product_price;
    TextView increse, decrese, total;
    ImageView product_image, close;

        private MyViewHolder(View itemView) {
            super(itemView);
            product_title = itemView.findViewById(R.id.text_product_title);
            product_desc = itemView.findViewById(R.id.text_product_desc);
            product_price = itemView.findViewById(R.id.text_product_price);
            increse = itemView.findViewById(R.id.text_increse);
            decrese = itemView.findViewById(R.id.text_decrese);
            total = itemView.findViewById(R.id.text_total);
            product_image = itemView.findViewById(R.id.image_product);
            close = itemView.findViewById(R.id.image_close);
        }
    }
}