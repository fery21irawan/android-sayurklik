package com.sayurklik.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sayurklik.app.adapters.ProfileAdapter;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.helpers.SayurKlikHelper;
import com.sayurklik.app.items.ProfileItem;
import com.sayurklik.app.models.OrderModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    ProfileAdapter adapter;
    List<ProfileItem> dataList;
    private ImageView ivProfil;
    private TextView tvName, tvPhone, tvLogout, tvTotalOrder;
    private AuthHelper authHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        authHelper = new AuthHelper(this);
        setTitle(authHelper.userdata(consts.FULLNAME));

        tvTotalOrder = findViewById(R.id.tv_total_order);
        tvName = findViewById(R.id.tv_name);
        tvPhone = findViewById(R.id.tv_phone);
        tvLogout = findViewById(R.id.tv_logout);
        tvLogout.setOnClickListener(this);
        ivProfil = findViewById(R.id.iv_profile);

        setList();
        setFragment();
        setUpProfilAccount();
        totalOrder();
    }
    private void totalOrder(){
        new OrderModel(this).showTotalOrderInto(tvTotalOrder);
    }
    private void setUpProfilAccount(){
        String phoneNumber  = authHelper.userdata(consts.PHONE).replaceFirst("0", "+62");
        String name         = authHelper.userdata(consts.FULLNAME);
        String image        = consts.AVATAR_URL + authHelper.userdata(consts.PROFIL_IMAGE);
        tvPhone.setText(phoneNumber);
        tvName.setText(name);
        Glide.with(this)
                .load(image)
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfil);
    }
    private void setFragment(){
        getSupportFragmentManager().beginTransaction().replace(
                R.id.frame_order,
                new OrderFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.frame_account,
                new AccountFragment()).commit();
    }
    private void setList(){
        dataList = new ArrayList<>();
        adapter = new ProfileAdapter(this, dataList);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
        String[] titleMenu = {
                "Daftar Tagihan",
                "Daftar Order",
                "Edit Account",
                "Ubah Password"
        };
        Class[] action = {
                TagihanActivity.class,
                OrderActivity.class,
                OrderActivity.class,
                OrderActivity.class
        };
        for (int i=0; i< titleMenu.length; i++){
            ProfileItem data = new ProfileItem();
            data.setTitle(titleMenu[i]);
            data.setaClass(action[i]);
            dataList.add(data);
        }
    }
    private void logout(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout dari akun ?");
        //builder.setMessage("Data anda di penyimpanan akan dihapus Seluruhnya ..");
        builder.setCancelable(false);
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                authHelper.logout();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog showDialog = builder.create();
        showDialog.show();
    }
    @Override
    public void onClick(View v) {
        if(v == tvLogout){
            logout();
        }
    }
}
