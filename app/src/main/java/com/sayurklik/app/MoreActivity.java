package com.sayurklik.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.sayurklik.app.adapters.MoreAdapter;
import com.sayurklik.app.adapters.ProfileAdapter;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.items.MoreItem;
import com.sayurklik.app.items.ProfileItem;

import java.util.ArrayList;
import java.util.List;

public class MoreActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    MoreAdapter adapter;
    List<MoreItem> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        AuthHelper authHelper = new AuthHelper(this);
        setTitle(authHelper.userdata(consts.FULLNAME));
        setList();
    }
    private void setList(){
        dataList = new ArrayList<>();
        adapter = new MoreAdapter(this, dataList);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
        String[] titleMenu = {
                "Tentang Kami",
                "Kebijakan & Privasi",
                "Bagikan Aplikasi",
                "Bantuan",
                "Logout"
        };
        for (int i=0; i< titleMenu.length; i++){
            MoreItem data = new MoreItem();
            data.setTitle(titleMenu[i]);
            dataList.add(data);
        }
    }
}
