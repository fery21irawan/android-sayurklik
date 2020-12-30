package com.sayurklik.app.models;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.sayurklik.app.consts;
import com.sayurklik.app.helpers.AuthHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SliderModel {
    private Context context;
    public SliderModel setSlider(Context context){
        this.context = context;
        return this;
    }
    public void into(final ImageSlider imageSlider){
        final ArrayList<SlideModel> imageList = new ArrayList<>();
        AndroidNetworking.get(consts.SLIDER)
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
                                imageList.add(new SlideModel(consts.BANNER_URL + object.getString(consts.SLIDER_IMAGE),null));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        imageSlider.setImageList(imageList, ScaleTypes.FIT);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
