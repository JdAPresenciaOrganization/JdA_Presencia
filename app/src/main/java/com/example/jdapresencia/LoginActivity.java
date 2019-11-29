package com.example.jdapresencia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jdapresencia.model.LoginPresenterImpl;
import com.example.jdapresencia.model.User;
import com.example.jdapresencia.presenter.LoginPresenter;
import com.example.jdapresencia.view.LoginView;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LoginActivity extends AppCompatActivity implements LoginView {

    //Variables
    EditText user, pass;
    Button bLogin;

    LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            usersFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        }



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

    @Override
    public void goToNextActivity(String userType) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("rol_key", userType);
        startActivity(intent);
    }

    protected void usersFile(Context context) throws IOException {
        String FILE_NAME = "/usersFile.dat";

        File file = new File(context.getFilesDir().getPath()+FILE_NAME);
        FileOutputStream fileout = new FileOutputStream(file);
        ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

        User user1 = new User("1", "admin", "admin", "admin");
        User user2 = new User("2", "trabajador", "worker", "worker");

        dataOS.writeObject(user1);
        dataOS.close();
    }

    public void readUsersFile(Context context) throws IOException {
        User readUser;

        String FILE_NAME = "/usersFile.dat";

        File file = new File(context.getFilesDir().getPath()+FILE_NAME);
        FileInputStream filein = new FileInputStream(file);
        ObjectInputStream dataIS = new ObjectInputStream(filein);

        try {
            while (true) {
                readUser = (User) dataIS.readObject();
                Log.i("@@@@@@@@@@@@@@@@@@@@@@@@@", readUser.getIdU());
                Log.i("@@@@@@@@@@@@@@@@@@@@@@@@@", readUser.getUsername());
            }
        } catch (EOFException eo) {
            Log.i("@@@@@@@@@@@@@@@@@@@@@@@@@@@", "finished reading");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        dataIS.close();
    }
}