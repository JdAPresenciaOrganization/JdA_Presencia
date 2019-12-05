package com.example.jdapresencia.model;

import android.content.Context;
import android.text.TextUtils;

import com.example.jdapresencia.LoginActivity;
import com.example.jdapresencia.presenter.LoginPresenter;
import com.example.jdapresencia.view.LoginView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static com.example.jdapresencia.LoginActivity.FILE_NAME;
import static com.example.jdapresencia.LoginActivity.getAppContext;

public class LoginPresenterImpl implements LoginPresenter {

    LoginView mLoginView;

    //Constructor
    public LoginPresenterImpl(LoginView mLoginView) {
        this.mLoginView = mLoginView;
    }

    @Override
    public void checkLogin(String user, String pass) {

        String userType, sessionUserId;
        File file = new File(getAppContext().getFilesDir().getPath()+FILE_NAME);


        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
            mLoginView.loginValidations();
        } else {
            try {
                ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(file));

                User usuario = (User) entrada.readObject();

                while (usuario!=null) {

                    if (user.equals(usuario.getUsername()) && pass.equals(usuario.getPassword())) {
                        sessionUserId = usuario.getIdU();
                        userType = usuario.getRol();
                        if (userType.equals("admin")) {
                            mLoginView.loginSuccessAdmin(sessionUserId, userType);
                            break;
                        } else if (userType.equals("trabajador")) {
                            mLoginView.loginSuccess(sessionUserId, userType);
                            break;
                        }
                    } else {
                        usuario = (User) entrada.readObject();
                    }
                }
            } catch (IOException e) {
                mLoginView.loginError();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}