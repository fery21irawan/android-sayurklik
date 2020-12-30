package com.sayurklik.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sayurklik.app.R;
import com.sayurklik.app.helpers.IntentHelper;
import com.sayurklik.app.items.ProfileItem;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<ProfileItem> itemList;
    public ProfileAdapter(Context context, List<ProfileItem> itemList){
        this.context = context;
        this.itemList = itemList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProfileAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = layoutInflater.inflate(R.layout.item_profile_menu, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.MyViewHolder holder, int position) {
        final ProfileItem item = itemList.get(position);
        holder.tv_title.setText(item.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentHelper intentHelper = new IntentHelper(context);
                intentHelper.intentForward(item.getaClass());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
}
