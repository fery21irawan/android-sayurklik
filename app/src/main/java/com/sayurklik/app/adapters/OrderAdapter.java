package com.sayurklik.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sayurklik.app.ConfirmActivity;
import com.sayurklik.app.OrderActivity;
import com.sayurklik.app.OrderDetailActivity;
import com.sayurklik.app.R;
import com.sayurklik.app.helpers.IntentHelper;
import com.sayurklik.app.items.OrderItem;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private List<OrderItem> items;
    private Context context;
    private LayoutInflater layoutInflater;
    public OrderAdapter(Context context, List<OrderItem> items){
        this.context = context;
        this.items = items;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = layoutInflater.inflate(R.layout.item_order, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, int position) {
        final OrderItem current = items.get(position);
        holder.tvOrderId.setText(current.getOrderId());
        holder.tvOrderDate.setText(current.getOrderDate());
        if ((Integer.parseInt(current.getStatus()) == 6)) {
            holder.btnUploadPembayaran.setVisibility(View.VISIBLE);
            holder.btnUploadPembayaran.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Uploading", Toast.LENGTH_SHORT).show();
                    new IntentHelper(context).intentForward(ConfirmActivity.class, current);
                }
            });
        } else {
            holder.btnUploadPembayaran.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentHelper intentHelper = new IntentHelper(context);
                intentHelper.intentForward(OrderDetailActivity.class, current);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderDate;
        Button btnUploadPembayaran;
        private MyViewHolder(View itemView){
            super(itemView);
            tvOrderDate = itemView.findViewById(R.id.tv_order_date);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
            btnUploadPembayaran = itemView.findViewById(R.id.btn_upload_pembayaran);
        }
    }
    /*
    * ===================================================
    *           STATUS ADAPTER FOR ORDER
    * ===================================================
    * */
    public static class Status extends RecyclerView.Adapter<Status.MyViewHolder>{

        private List<OrderItem.Status> items;
        private Context context;
        private LayoutInflater layoutInflater;
        public Status(Context context, List<OrderItem.Status> items){
            this.context = context;
            this.items = items;
            layoutInflater = LayoutInflater.from(context);
        }
        @NonNull
        @Override
        public Status.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            view = layoutInflater.inflate(R.layout.item_order_list, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull Status.MyViewHolder holder, int position) {
            final OrderItem.Status current = items.get(position);
            holder.tvStatusName.setText(current.getStatusName());
            holder.tvTotalRow.setText(current.getTotalRows());
            int statusOrder = Integer.parseInt(current.getStatusId());
            /*if(statusOrder == 6){
                holder.tvTotalRow.setText("Urgent " + current.getTotalRows());
            }*/
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Integer.parseInt(current.getTotalRows()) > 0){
                        IntentHelper intentHelper = new IntentHelper(context);
                        intentHelper.intentForward(OrderActivity.class, current);
                    }else{
                        Toast.makeText(context, "Belum ada order yang " + current.getStatusName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvStatusName, tvTotalRow;
            private MyViewHolder(View itemView){
                super(itemView);
                tvStatusName  = itemView.findViewById(R.id.tv_status_name);
                tvTotalRow = itemView.findViewById(R.id.tv_total_row);
            }
        }
    }
}
