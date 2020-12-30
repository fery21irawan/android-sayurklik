package com.sayurklik.app.models;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.sayurklik.app.TagihanActivity;
import com.sayurklik.app.TagihanFragment;
import com.sayurklik.app.adapters.TagihanAdapter;
import com.sayurklik.app.consts;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.helpers.ProgressBarHelper;
import com.sayurklik.app.items.TagihanItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TagihanModel {
    public static class SetupViewPager extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SetupViewPager(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void setFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public static class Get {
        private Context context;
        private List<TagihanItem> itemList = new ArrayList<>();
        private TagihanAdapter adapter;
        private JSONArray array;
        private ProgressBarHelper mPbh;
        //query parameter
        private String key = "";
        private String value = "";
        //pagination
        private int start;
        private int limit;

        public Get(Context context) {
            this.context = context;
        }

        public Get setData(JSONArray array) {
            this.array = array;
            return this;
        }
        public Get setProgressBar(ProgressBarHelper pbh){
            this.mPbh = pbh;
            return this;
        }
        public Get setQueryParameter(String key, String value){
            this.key = key;
            this.value = value;
            return this;
        }

        public void showInto(RecyclerView recyclerView) {
            adapter = new TagihanAdapter(context, itemList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            getTagihan();
        }

        private void getTagihan() {
            itemList.clear();
            AndroidNetworking.get(consts.TAGIHAN)
                    .addHeaders(new AuthHelper(context).basicAuth())
                    .addQueryParameter(key, value)
                    .addQueryParameter(consts.USER_ID, new AuthHelper(context).userdata(consts.ID))
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Tagihan", response.toString());
                            try {
                                JSONArray array = response.getJSONArray("data");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    //do
                                    TagihanItem item = new TagihanItem();
                                    item.setId(object.getString(consts.ID));
                                    item.setOrderId(object.getString(consts.ORDER_ID));
                                    itemList.add(item);
                                    if(mPbh.isShown()){
                                        mPbh.clearAnimation();
                                        mPbh.setVisibility(View.GONE);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
            /*try {
                for (int i = 0; i < array.length(); i++) {
                    TagihanItem item = new TagihanItem();
                    JSONObject object = array.getJSONObject(i);
                    item.setId(object.getString(consts.ID));
                    item.setOrderId(object.getString(consts.ORDER_ID));
                    item.setConfirmDate(object.getString("confirm_date"));
                    item.setImagePayment(object.getString("image_payment"));
                    item.setStatus(object.getString(consts.STATUS));
                    itemList.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            adapter.notifyDataSetChanged();
            //make request or get data from sqlite in below
        }
    }
}
