package com.sayurklik.app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.sayurklik.app.MainActivity;
import com.sayurklik.app.ProductFragment;
import com.sayurklik.app.R;
import com.sayurklik.app.consts;
import com.sayurklik.app.items.CategoryItem;

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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private LayoutInflater inflanter;
    private Context context;
    private List<CategoryItem> data;

    public CategoryAdapter(Context context, List<CategoryItem> data) {
        this.context = context;
        inflanter = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        MyViewHolder holder = null;
        view = inflanter.inflate(R.layout.item_nav, parent, false);
        holder = new MyViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CategoryItem current = data.get(position);
        holder.title.setText(current.getmCategoryName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close drawer
                MainActivity.closeDrawer();
                //passing data
                Bundle bundle = new Bundle();
                bundle.putParcelable(consts.PARCELABLE, current);
                ProductFragment productFragment = new ProductFragment();
                productFragment.setArguments(bundle);
                ((FragmentActivity)v.getContext())
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.view, productFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

    TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
        }
    }
}