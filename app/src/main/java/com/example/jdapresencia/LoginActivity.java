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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements LoginView {

    public static String FILE_NAME = "/usersFile.dat";
    private static Context context;

    //Variables
    EditText user, pass;
    Button bLogin;

    LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginActivity.context = getApplicationContext();

        createUsersFile(this);

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
    public void loginSuccessAdmin(String sessionUserId, String userType) {
        //Toast.makeText(getApplicationContext(), "Login OK ADMIN",Toast.LENGTH_SHORT).show();
        goToNextActivity(sessionUserId, userType);
    }

    @Override
    public void loginSuccess(String sessionUserId, String userType) {
        //Toast.makeText(getApplicationContext(), "Login OK USER",Toast.LENGTH_SHORT).show();
        goToNextActivity(sessionUserId, userType);
    }

    @Override
    public void loginError() {
        Toast.makeText(getApplicationContext(), "Login not ok",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToNextActivity(String sessionUserId, String userType) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("idSession_key", sessionUserId);
        intent.putExtra("rol_key", userType);
        startActivity(intent);
    }

    /**
     * Para primera vez que se inicia la app se mira si existe el fichero .dat,
     * si no, se crea con el admin y un primer trabajador de muestra
     * @param context
     */
    protected void createUsersFile(Context context) {
        try {
            //Fichero .dat guardado en /data/data/com.example.jdapresencia -> files
            File file = new File(context.getFilesDir().getPath()+FILE_NAME);

            if(!file.exists()){
                /* Creación de arrayList con un admin y un trabajador
                   que se guardarán en un fichero serializado */
                ArrayList<User> userListFile = new ArrayList<>();

                /* De momento el administrador de la aplicación usará este usuario, no se podrán
                   crear más administradores (de momento), el trabajador es para testear,
                    en la aplicación se podrán añadir nuevos trabajadores */
                User user1 = new User("1", "admin", "super", "1234");
                User user2 = new User("2", "admin", "admin", "1234");
                User user3 = new User("3", "trabajador", "worker", "1234");
                User user4 = new User("4", "trabajador", "worker2", "1234");
                User user5 = new User("5", "trabajador", "worker3", "1234");

                FileOutputStream fileout = new FileOutputStream(file);
                ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

                userListFile.add(user1);
                userListFile.add(user2);
                userListFile.add(user3);
                userListFile.add(user4);
                userListFile.add(user5);

                dataOS.writeObject(userListFile);
                dataOS.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Función que devuelve el contexto de LoginActivity
     * @return
     */
    public static Context getAppContext() {
        return LoginActivity.context;
    }
}