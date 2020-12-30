package com.sayurklik.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.sayurklik.app.CaraBayarFragment;
import com.sayurklik.app.R;
import com.sayurklik.app.consts;
import com.sayurklik.app.items.PaymentItem;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<PaymentItem> itemList;
    public PaymentAdapter(Context context, List<PaymentItem> itemList) {
        this.context = context;
        this.itemList = itemList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PaymentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = layoutInflater.inflate(R.layout.item_payment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.MyViewHolder holder, int position) {
        final PaymentItem item = itemList.get(position);
        holder.tvBankName.setText(item.getBankName());
        holder.tvBankRek.setText("No. Rekening " + item.getBankRekening());
        holder.tvAn.setText("a.n. " + item.getAn());
        holder.btnCaraBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getSupportFragmentManager
               // new CaraBayarFragment();
                FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
                String title = String.valueOf(((FragmentActivity) context).getTitle());
                Bundle bundle = new Bundle();
                bundle.putParcelable(consts.PARCELABLE, item);
                bundle.putString("notrx", title);
                CaraBayarFragment caraBayarFragment = new CaraBayarFragment();
                caraBayarFragment.setArguments(bundle);
                caraBayarFragment.show(fm, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvBankName, tvBankRek, tvAn;
        Button btnCaraBayar;
        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBankName  = itemView.findViewById(R.id.tv_bank_name);
            tvBankRek   = itemView.findViewById(R.id.tv_bank_rekening);
            tvAn        = itemView.findViewById(R.id.tv_an);
            btnCaraBayar = itemView.findViewById(R.id.btn_cara_bayar);
        }
    }
}
