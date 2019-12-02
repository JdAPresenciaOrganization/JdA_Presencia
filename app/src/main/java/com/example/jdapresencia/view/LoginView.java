package com.example.jdapresencia.view;

public interface LoginView {

    void loginValidations();
    void loginSuccessAdmin(String sessionUserId, String userType);
    void loginSuccess(String sessionUserId, String userType);
    void loginError();
    void goToNextActivity(String sessionUserId, String userType);
}
