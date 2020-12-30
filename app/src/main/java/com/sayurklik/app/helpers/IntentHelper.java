package com.sayurklik.app.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.sayurklik.app.MainActivity;
import com.sayurklik.app.consts;

import static android.app.Activity.RESULT_OK;

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

public class IntentHelper {
/*
* this class require animation class by animatoo
* add this script into build.gradle (Module: app)
* implementation 'com.github.mohammadatif:Animatoo:master'
* */
    private Context mContext;
    public IntentHelper(Context context){
        this.mContext = context;
    }
    public IntentHelper(Context context, Class action, String animation){
        context.startActivity(new Intent(context, action));
        switch (animation){
            case "up":
                Animatoo.animateSlideUp(context);
                break;
            case "down":
                Animatoo.animateSlideDown(context);
                break;
            case "left":
                Animatoo.animateSlideLeft(context);
                break;
            case "right":
                Animatoo.animateSlideRight(context);
                break;
        }
    }
    public void setIntent(Class action, String animation){
        //((Activity)context).startActivity(intent);
        mContext.startActivity(new Intent(mContext, action));
        switch (animation){
            case "up":
                Animatoo.animateSlideUp(mContext);
                break;
            case "down":
                Animatoo.animateSlideDown(mContext);
                break;
            case "left":
                Animatoo.animateSlideLeft(mContext);
                break;
            case "right":
                Animatoo.animateSlideRight(mContext);
                break;
        }
    }
    public void intentForward(Class action){
        mContext.startActivity(new Intent(mContext, action));
        Animatoo.animateSlideRight(mContext);
    }
    public void intentForward(Class action, Parcelable parcelable){
        Intent intent = new Intent(mContext, action);
        intent.putExtra(consts.PARCELABLE, parcelable);
        mContext.startActivity(intent);
        Animatoo.animateSlideRight(mContext);
    }
    public void intentBack(Class action){
        mContext.startActivity(new Intent(mContext, action));
        Animatoo.animateSlideLeft(mContext);
        ((Activity)mContext).finish();
    }
    public void intentBackToParent(){
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
        Animatoo.animateSlideLeft(mContext);
        //((Activity)mContext).finish();
    }
    public void intentBackResult(Parcelable parcelable){
        Intent intent = new Intent();
        intent.putExtra(consts.PARCELABLE, parcelable);
        ((Activity) mContext).setResult(RESULT_OK, intent);
        ((Activity) mContext).finish();
        Animatoo.animateSlideDown(mContext);
    }
    public void intentForwardResult(Class action, int params){
        Intent intent = new Intent(mContext, action);
        ((Activity)mContext).startActivityForResult(intent, params);
        Animatoo.animateSlideUp(mContext);
    }
}
