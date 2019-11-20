package com.example.jdapresencia.model;

import android.text.TextUtils;

import com.example.jdapresencia.presenter.LoginPresenter;
import com.example.jdapresencia.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter {

    LoginView mLoginView;

    //Constructor
    public LoginPresenterImpl(LoginView mLoginView) {
        this.mLoginView = mLoginView;
    }

    @Override
    public void checkLogin(String user, String pass) {
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
            mLoginView.loginValidations();
        } else if (user.equals("admin") && pass.equals("1234")){
            mLoginView.loginSuccess();
        } else {
            mLoginView.loginError();
        }
    }

    /*
    public Boolean checkLogin(String user, String pass){

    }
     */
}
