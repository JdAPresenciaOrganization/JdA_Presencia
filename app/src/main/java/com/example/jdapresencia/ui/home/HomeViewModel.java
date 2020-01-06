package com.example.jdapresencia.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jdapresencia.MVVMRepository;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(MVVMRepository.spanishDate());
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void userCheckIn(String idSession) {
        MVVMRepository.userCheckIn(idSession);
    }

    public void userCheckOut(String idSession) {
        MVVMRepository.userCheckOut(idSession);
    }
}