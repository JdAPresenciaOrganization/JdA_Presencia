package com.example.jdapresencia.ui.buscador_trabajadores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BuscadorTrabajadoresViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BuscadorTrabajadoresViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es el buscador de trabajadores");
    }

    public LiveData<String> getText() {
        return mText;
    }
}