package com.example.jdapresencia.model;

import android.content.Context;
import android.text.TextUtils;

import com.example.jdapresencia.LoginActivity;
import com.example.jdapresencia.presenter.LoginPresenter;
import com.example.jdapresencia.view.LoginView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class LoginPresenterImpl implements LoginPresenter {

    LoginView mLoginView;
    int userFound = 0;

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
                    userFound = 1;
                } else if (user.equals(getUserList().get(i).getUsername()) && pass.equals(getUserList().get(i).getPassword())
                        && getUserList().get(i).getRol().equals("trabajador")){
                    mLoginView.loginSuccess();
                    userFound = 1;
                }
            }
            if (userFound != 1){
                mLoginView.loginError();
            }
        }
    }

    @Override
    public ArrayList<User> getUserList() {
        Context context = LoginActivity.getAppContext();
        ArrayList<User> userList = new ArrayList<>();
        try {
            File file = new File(context.getFilesDir().getPath() + LoginActivity.FILE_NAME);

            FileInputStream filein = new FileInputStream(file);
            ObjectInputStream dataIS = new ObjectInputStream(filein);

            for (User userFromFileList : (ArrayList<User>) dataIS.readObject()) {
                userList.add(userFromFileList);
            }

            dataIS.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException t) {
            t.printStackTrace();
        }
        return userList;
    }
}