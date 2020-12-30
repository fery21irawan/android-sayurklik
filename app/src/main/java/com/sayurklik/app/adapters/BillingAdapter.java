package com.sayurklik.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sayurklik.app.CheckoutActivity;
import com.sayurklik.app.R;
import com.sayurklik.app.items.BillingItem;

import java.util.List;

public class BillingAdapter extends RecyclerView.Adapter<BillingAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<BillingItem> itemList;

    private static int lastChecked = -1;
    private static boolean isCheckedBilling = false;

    public BillingAdapter(Context context, List<BillingItem> itemList) {
        this.context = context;
        this.itemList = itemList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BillingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = layoutInflater.inflate(R.layout.item_billing, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BillingAdapter.MyViewHolder holder, final int position) {
        final BillingItem item = itemList.get(position);
        holder.rbBilling.setText(item.getBillingName());
        holder.rbBilling.setChecked(lastChecked == position);
    }
    private void isBillingChecked(){
        isCheckedBilling = true;
    }


    public static boolean isCheckedBilling() {
        return isCheckedBilling;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RadioButton rbBilling;
        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rbBilling = itemView.findViewById(R.id.rb_billing);
            rbBilling.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastChecked = getAdapterPosition();
                    notifyDataSetChanged();
                    BillingItem item = itemList.get(lastChecked);
                    //atur status metode pembayaran
                    isBillingChecked();
                    CheckoutActivity.billingItem.setBillingId(item.getBillingId());
                    CheckoutActivity.billingItem.setBillingName(item.getBillingName());
                    CheckoutActivity.getBilling();
                }
            });
        }
    }
}
