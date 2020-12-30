package com.sayurklik.app.helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.sayurklik.app.LoginActivity;
import com.sayurklik.app.consts;

import java.util.HashMap;
import java.util.Map;

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

public class AuthHelper {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public AuthHelper(Context context){
        this.context=context;
        this.preferences = context.getSharedPreferences(consts.USERLOGIN, Context.MODE_PRIVATE);
    }
    public String userdata(String value) {
        return preferences.getString(value, "");
    }
    public boolean isLogin(){
        return preferences.getBoolean(String.valueOf(consts.ISLOGIN), false);
    }
    public Map<String, String> basicAuth(){
        Map<String, String> mapBasic = new HashMap<>();
        mapBasic.put("authorization", Credentials.basic("sayurklikcredential", "kamisenamagustusduapuluh"));
        return mapBasic;
    }
    @SuppressLint("CommitPrefEdits")
    public void logout(){
        this.editor = preferences.edit();
        this.editor.clear();
        this.editor.apply();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();
        Animatoo.animateSlideRight(context);
    }
}
