package com.example.jdapresencia.presenter;

import com.example.jdapresencia.User;

import java.util.ArrayList;

public interface LoginPresenter {

    void checkLogin(String user, String pass);
    ArrayList<User> getUserList();
}
