package com.sayurklik.app.models;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.sayurklik.app.adapters.CategoryAdapter;
import com.sayurklik.app.consts;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.helpers.ProgressBarHelper;
import com.sayurklik.app.items.CategoryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NavigationModel {
    public static class Get{
        private Context context;
        private List<CategoryItem> itemList = new ArrayList<>();
        private CategoryAdapter adapter;
        private ProgressBarHelper mPbh;
        //query parameter
        private String key = "";
        private String value = "";
        //pagination
        private int start;
        private int limit;

        public Get(Context context){
            this.context = context;
        }
        public Get setQueryParameter(String key, String value){
            this.key = key;
            this.value = value;
            return this;
        }
        public Get setProgressBar(ProgressBarHelper pbh){
            this.mPbh = pbh;
            return this;
        }
        public void showInto(RecyclerView recyclerView){
            adapter = new CategoryAdapter(context, itemList);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            get();
        }

        private void get(){
            itemList.clear();
            /*Load item menu from category in server*/
            AndroidNetworking.get(consts.CATEGORY)
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
                                    CategoryItem data = new CategoryItem();
                                    data.setmId(object.getString(consts.ID));
                                    data.setmCategoryName(object.getString(consts.CATEGORY_NAME));
                                    itemList.add(data);
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
