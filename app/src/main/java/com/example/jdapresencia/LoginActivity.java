package com.example.jdapresencia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private static Context mContext;

    //Variables
    EditText user, pass;
    Button bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        //Recoger valores del layout en variables
        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        bLogin = findViewById(R.id.login_button);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MVVMRepository.checkLogin(user.getText().toString(), pass.getText().toString());
            }
        });
    }

    /**
     * Se envia al MainActivity el id del usuario y su rol
     * @param sessionUserId
     * @param userType
     */
    public static void loginSuccess(String sessionUserId, String userType) {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra("idSession_key", sessionUserId);
        intent.putExtra("rol_key", userType);
        mContext.startActivity(intent);
    }
}