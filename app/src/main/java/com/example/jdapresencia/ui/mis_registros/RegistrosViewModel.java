package com.example.jdapresencia.ui.mis_registros;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jdapresencia.MVVMRepository;
import com.example.jdapresencia.model.Registro;

import java.util.ArrayList;

public class RegistrosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RegistrosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is MIS REGISTROS fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public ArrayList<Registro> getListRegistros(String idSession) {
        return MVVMRepository.getUserRegisterFile(idSession);
    }
}