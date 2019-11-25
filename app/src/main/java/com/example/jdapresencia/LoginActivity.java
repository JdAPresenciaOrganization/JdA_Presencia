package com.example.jdapresencia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jdapresencia.model.LoginPresenterImpl;
import com.example.jdapresencia.presenter.LoginPresenter;
import com.example.jdapresencia.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {

    //Variables
    EditText user, pass;
    Button bLogin;

    LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Recoger valores del layout en variables
        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        bLogin = findViewById(R.id.login_button);

        mLoginPresenter = new LoginPresenterImpl(LoginActivity.this);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.checkLogin(user.getText().toString(), pass.getText().toString());
            }
        });
    }

    @Override
    public void loginValidations() {
        Toast.makeText(getApplicationContext(), "Enter username and password",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccessAdmin() {
        //Toast.makeText(getApplicationContext(), "Login OK ADMIN",Toast.LENGTH_SHORT).show();
        goToNextActivity("admin");
    }

    @Override
    public void loginSuccess() {
        //Toast.makeText(getApplicationContext(), "Login OK USER",Toast.LENGTH_SHORT).show();
        goToNextActivity("trabajador");
    }

    @Override
    public void loginError() {
        Toast.makeText(getApplicationContext(), "Login not ok",Toast.LENGTH_SHORT).show();
    }

    protected void goToNextActivity(String tipo){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("rol_key", tipo);
        startActivity(intent);
    }
}