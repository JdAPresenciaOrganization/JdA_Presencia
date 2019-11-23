package com.example.jdapresencia.ui.mis_registros;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegistrosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RegistrosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is MIS REGISTROS fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}