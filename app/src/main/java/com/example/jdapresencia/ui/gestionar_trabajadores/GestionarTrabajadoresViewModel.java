package com.example.jdapresencia.ui.gestionar_trabajadores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GestionarTrabajadoresViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GestionarTrabajadoresViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gestionar trabajadores fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}