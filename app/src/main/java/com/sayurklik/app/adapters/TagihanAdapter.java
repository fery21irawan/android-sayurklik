package com.sayurklik.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sayurklik.app.R;
import com.sayurklik.app.TagihanDetailActivity;
import com.sayurklik.app.helpers.IntentHelper;
import com.sayurklik.app.items.TagihanItem;

import java.util.List;

public class TagihanAdapter extends RecyclerView.Adapter<TagihanAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<TagihanItem> itemList;

    public TagihanAdapter(Context context, List<TagihanItem> itemList) {
        this.context = context;
        this.itemList = itemList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TagihanAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = layoutInflater.inflate(R.layout.item_tagihan, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagihanAdapter.MyViewHolder holder, int position) {
        final TagihanItem item = itemList.get(position);
        holder.tvTagihanId.setText(item.getOrderId());
        holder.tvConfirmDate.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentHelper(context).intentForward(TagihanDetailActivity.class, item);
               /* IntentHelper intentHelper = new IntentHelper(context);
                intentHelper.intentForward(item.getaClass());*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTagihanId, tvConfirmDate;
        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTagihanId = itemView.findViewById(R.id.tv_tagihan_id);
            tvConfirmDate = itemView.findViewById(R.id.tv_tagihan_date);
        }
    }
}
