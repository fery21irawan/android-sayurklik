package com.sayurklik.app.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.sayurklik.app.OrderDetailActivity;
import com.sayurklik.app.R;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.items.MoreItem;

import java.util.List;

public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.MyViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<MoreItem> itemList;
    public MoreAdapter(Context context, List<MoreItem> itemList){
        this.context = context;
        this.itemList = itemList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = layoutInflater.inflate(R.layout.item_profile_menu, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MoreItem item = itemList.get(position);
        holder.tv_title.setText(item.getTitle());
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (getPosition()) {
                        case 0:
                            /*String url_tentang = Endpoints.Base_URL+"tentang_kami.html";
                            gotoURL(url_tentang);*/
                            Toast.makeText(context, "Tentang", Toast.LENGTH_SHORT).show();
                            break;

                        case 1:
                            /*String url_bantuan = Endpoints.Base_URL+"bantuan.html";
                            gotoURL(url_bantuan);*/
                            Toast.makeText(context, "Kebijakan", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, OrderDetailActivity.class));
                            break;
                        case 2:
                            Intent sendint = new Intent(Intent.ACTION_SEND);
                            sendint.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
                            sendint.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.app_name) + "\" \nhttps://play.google.com/store/apps/details?id=" + context.getPackageName());
                            sendint.setType("text/plain");
                            context.startActivity(Intent.createChooser(sendint, "Bagikan Aplikasi"));
                            break;

                        case 3:
                            Toast.makeText(context, "Bantuan", Toast.LENGTH_SHORT).show();
                            break;

                        case 4:
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                            alertDialogBuilder.setMessage("Logout ?");
                            alertDialogBuilder.setPositiveButton("Ya",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            AuthHelper authHelper = new AuthHelper(context);
                                            authHelper.logout();
                                        }
                                    });

                            alertDialogBuilder.setNegativeButton("Tidak",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {

                                        }
                                    });

                            //Showing the alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                            break;
                    }

                }
            });
        }
    }
}
