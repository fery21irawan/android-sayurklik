package com.sayurklik.app.adapters.feeds;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sayurklik.app.ProductFragment;
import com.sayurklik.app.R;
import com.sayurklik.app.consts;
import com.sayurklik.app.items.CategoryItem;
import com.sayurklik.app.items.FeedItem;
import com.sayurklik.app.items.ProductItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FeedHeaderAdapter extends RecyclerView.Adapter<FeedHeaderAdapter.MyViewHolder> {
    private LayoutInflater inflanter;
    private Context context;
    private List<FeedItem> data;

    public FeedHeaderAdapter(Context context, List<FeedItem> dataList){
        this.context = context;
        inflanter = LayoutInflater.from(context);
        this.data = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        MyViewHolder holder = null;
        view = inflanter.inflate(R.layout.item_product_feed_header, parent, false);
        holder = new MyViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final FeedItem current = data.get(position);
        holder.title.setText(current.getmCategoryName());
        holder.view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryItem item = new CategoryItem();
                item.setmId(current.getmId());
                item.setmCategoryName(current.getmCategoryName());
                Bundle bundle = new Bundle();
                bundle.putParcelable(consts.PARCELABLE, item);
                ProductFragment productFragment = new ProductFragment();
                productFragment.setArguments(bundle);
                ((FragmentActivity)v.getContext())
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.view, productFragment).commit();
            }
        });
        List<ProductItem> productList = new ArrayList<>();
        FeedAdapter adapter = new FeedAdapter(context, productList);
        holder.recyclerView.setAdapter(adapter);
        try {
            JSONArray array = new JSONArray(current.getmData());
            for (int i=0; i < array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                ProductItem data = new ProductItem();
                data.setmProduct_name(object.getString(consts.PRODUCT_NAME));
                data.setmProduct_description(object.getString(consts.PRODUCT_DESC));
                data.setmProduct_price(object.getString(consts.PRODUCT_PRICE));
                data.setmProduct_thumbs(object.getString(consts.PRODUCT_THUMBS));
                data.setmProduct_image(object.getString(consts.PRODUCT_IMAGE));
                productList.add(data);
                Log.d("from adapter", String.valueOf(i%2));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, view_all;
        RecyclerView recyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            view_all = itemView.findViewById(R.id.text_view_all);
            recyclerView = itemView.findViewById(R.id.recyclerview);
            //RecyclerView.LayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            RecyclerView.LayoutManager manager = new GridLayoutManager(context,2,GridLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(manager);
            recyclerView.setHasFixedSize(true);
        }
    }
}