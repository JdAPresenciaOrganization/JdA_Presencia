package com.example.jdapresencia.ui.user_profile;

import androidx.lifecycle.ViewModel;

import com.example.jdapresencia.MVVMRepository;

public class UserProfileViewModel extends ViewModel {

    public void userProfileData(String upName, String upNumber, String upEmail, String idSession) {
        MVVMRepository.userProfileData(upName, upNumber, upEmail, idSession);
    }
}
