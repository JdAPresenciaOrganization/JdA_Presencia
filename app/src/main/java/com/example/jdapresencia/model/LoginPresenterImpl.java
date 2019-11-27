package com.example.jdapresencia.model;

import android.text.TextUtils;

import com.example.jdapresencia.presenter.LoginPresenter;
import com.example.jdapresencia.view.LoginView;

import java.util.ArrayList;

public class LoginPresenterImpl implements LoginPresenter {

    LoginView mLoginView;
    Boolean userLoginOK = false;

    //Constructor
    public LoginPresenterImpl(LoginView mLoginView) {
        this.mLoginView = mLoginView;
    }

    @Override
    public void checkLogin(String user, String pass) {
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
            mLoginView.loginValidations();
        } else {
            for (int i = 0; i < getUserList().size(); i++){
                if (user.equals(getUserList().get(i).getUsername()) && pass.equals(getUserList().get(i).getPassword())
                    && getUserList().get(i).getRol().equals("admin")) {
                    mLoginView.loginSuccessAdmin();
                    userLoginOK = true;
                } else if (user.equals(getUserList().get(i).getUsername()) && pass.equals(getUserList().get(i).getPassword())
                        && getUserList().get(i).getRol().equals("trabajador")){
                    mLoginView.loginSuccess();
                    userLoginOK = true;
                }

                if (!userLoginOK){
                    mLoginView.loginError();
                }
            }
        }
    }

    @Override
    public ArrayList<User> getUserList() {
        ArrayList<User> userList = new ArrayList<>();

        User user1 = new User("1", "admin", "admin", "admin");
        User user2 = new User("2", "trabajador", "employee", "1234");

        userList.add(user1);
        userList.add(user2);

        return userList;
    }
}