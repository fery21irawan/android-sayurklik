package com.sayurklik.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sayurklik.app.helpers.AuthHelper;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final AuthHelper auth = new AuthHelper(this);
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 2 seconds
                    sleep(2 * 1000);
                    if (auth.isLogin()) {
                        /*jika sudah login*/
                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    } else {
                        // jika belum login
                        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    }
                    Animatoo.animateSlideLeft(SplashScreenActivity.this);

                    // After 5 seconds redirect to another intent

                    //Remove activity
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        // start thread
        background.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
