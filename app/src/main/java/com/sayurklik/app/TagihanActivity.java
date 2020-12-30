package com.sayurklik.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.tabs.TabLayout;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.models.AuthModel;
import com.sayurklik.app.models.TagihanModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TagihanActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagihan);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(final ViewPager viewPager) {
        final TagihanModel.SetupViewPager adapter = new TagihanModel.SetupViewPager(getSupportFragmentManager());
        AndroidNetworking.get(consts.TAGIHAN +"/status")
                .addHeaders(new AuthHelper(this).basicAuth())
                .addQueryParameter(consts.USER_ID, new AuthHelper(this).userdata(consts.ID))
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray(consts.DATA);
                            for (int i = 0; i < array.length(); i++) {
                                String statusName = array.getJSONObject(i).getString("status_name");
                                String statusId = array.getJSONObject(i).getString("id");
                                //JSONArray jsonArray = array.getJSONObject(i).getJSONArray("data");
                                Bundle bundle = new Bundle();
                                bundle.putString(consts.STATUS, statusId);
                                //bundle.putString(consts.DATA, jsonArray.toString());
                                TagihanFragment tagihanFragment = new TagihanFragment();
                                tagihanFragment.setArguments(bundle);
                                adapter.setFragment(tagihanFragment, statusName);
                                viewPager.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
