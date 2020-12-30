package com.sayurklik.app.models;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.sayurklik.app.adapters.feeds.FeedHeaderAdapter;
import com.sayurklik.app.consts;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.helpers.ProgressBarHelper;
import com.sayurklik.app.items.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FeedModel {
    public static class Get{
        private Context context;
        FeedHeaderAdapter adapter;
        List<FeedItem> itemList = new ArrayList<>();
        private ProgressBarHelper mPbh;
        //pagination
        private int start;
        private int limit;

        public Get(Context context){
            this.context = context;
        }

        public Get setProgressBar(ProgressBarHelper pbh){
            this.mPbh = pbh;
            return this;
        }
        public void showInto(RecyclerView recyclerView){
            adapter = new FeedHeaderAdapter(context,itemList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            get();
        }

        private void get(){
            itemList.clear();
            AndroidNetworking.get(consts.FEED)
                    .addHeaders(new AuthHelper(context).basicAuth())
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray array = response.getJSONArray(consts.DATA);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    FeedItem data = new FeedItem();
                                    data.setmId(object.getString(consts.ID));
                                    data.setmCategoryName(object.getString(consts.CATEGORY_NAME));
                                    data.setmData(object.getString(consts.DATA));
                                    itemList.add(data);
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
        }
    }
}
