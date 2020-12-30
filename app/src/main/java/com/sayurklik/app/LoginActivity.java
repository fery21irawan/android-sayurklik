package com.sayurklik.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sayurklik.app.helpers.IntentHelper;
import com.sayurklik.app.models.AuthModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditTextUser, mEditTextPass;
    private TextView tvCreateAccount;
    private Button mButtonSign;
    private AuthModel authModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        authModel = new AuthModel(this);
        mEditTextUser = findViewById(R.id.editEmail);
        mEditTextPass = findViewById(R.id.editPassword);
        mButtonSign = findViewById(R.id.btnLogin);
        tvCreateAccount = findViewById(R.id.tv_create_account);
        mButtonSign.setOnClickListener(this);
        tvCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mButtonSign){
            String user = mEditTextUser.getText().toString();
            String pass = mEditTextPass.getText().toString();
            authModel.login(user, pass);
        }
        if(v == tvCreateAccount){
            new IntentHelper(LoginActivity.this).intentForward(RegisterActivity.class);
        }
    }
}
