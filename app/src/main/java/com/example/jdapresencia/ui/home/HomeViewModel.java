package com.example.jdapresencia.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jdapresencia.MVVMRepository;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void userCheckIn() {
        MVVMRepository.userCheckIn();
    }

    public void userCheckOut() {
        MVVMRepository.userCheckOut();
    }
}