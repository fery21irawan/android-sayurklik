package com.sayurklik.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sayurklik.app.helpers.IntentHelper;

public class RegisterActivity extends AppCompatActivity {
    TextView tvLoginAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tvLoginAccount = findViewById(R.id.tv_login_account);
        tvLoginAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentHelper(RegisterActivity.this).intentBack(LoginActivity.class);
            }
        });
    }
}
