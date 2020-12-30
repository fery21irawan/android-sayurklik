package com.sayurklik.app.models;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sayurklik.app.MainActivity;
import com.sayurklik.app.consts;
import com.sayurklik.app.helpers.AuthHelper;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Credentials;


/*
 * Copyright (c) 2020. by Fery Irawan
 * Contact Person :
 * facebook : https://facebook.com/fery21irawan
 * instagram : https://instagram.com/fery21irawan
 * Phone : (+62) 822 3406 8387
 * Whatsapp : (+62) 822 3406 8387
 * site : https://fery21irawan.my.id
 * Address : Jl. Desa Pamalian RT. 05 RW. 02 Pamalian Kec. Kota Besi
 */

public class AuthModel {
    private Context mContext;

    public AuthModel(Context context){
        this.mContext = context;
    }
    public void login(final String username, final String password){
        /*firebase*/
        String token = FirebaseInstanceId.getInstance().getToken();
        final ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setMessage("Doing something, please wait.");
        dialog.show();
        AndroidNetworking.post(consts.AUTH)
                .addHeaders(new AuthHelper(mContext).basicAuth())
                .addUrlEncodeFormBodyParameter(consts.USERNAME, username)
                .addUrlEncodeFormBodyParameter(consts.PASSWORD, password)
                .addUrlEncodeFormBodyParameter("token", token)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean status = Boolean.parseBoolean(response.getString(consts.STATUS));
                            Log.d("pesan", response.toString());
                            if(status){
                                JSONObject data = response.getJSONObject(consts.DATA);
                                SharedPreferences preferences = mContext.getSharedPreferences(consts.USERLOGIN, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean(String.valueOf(consts.ISLOGIN), true);
                                editor.putString(consts.ID, data.getString(consts.ID));
                                editor.putString(consts.USERNAME, data.getString(consts.USERNAME));
                                editor.putString(consts.FULLNAME, data.getString(consts.FULLNAME));
                                editor.putString(consts.EMAIL, data.getString(consts.EMAIL));
                                editor.putString(consts.ADDRESS, data.getString(consts.ADDRESS));
                                editor.putString(consts.PHONE, data.getString(consts.PHONE));
                                editor.putString(consts.WA, data.getString(consts.WA));
                                editor.putString(consts.PROFIL_IMAGE, data.getString(consts.PROFIL_IMAGE));
                                editor.putString(consts.USER_LEVEL, data.getString(consts.USER_LEVEL));
                                editor.apply();
                                mContext.startActivity(new Intent(mContext, MainActivity.class));
                                Animatoo.animateZoom(mContext);
                                ((Activity)mContext).finish();
                            } else{
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                                Toast.makeText(mContext, response.getString(consts.MESSAGE), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("exc", e.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("anError", anError.getResponse().toString());
                    }
                });
    }
}
