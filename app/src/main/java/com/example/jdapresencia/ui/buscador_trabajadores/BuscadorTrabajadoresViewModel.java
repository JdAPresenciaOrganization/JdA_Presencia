package com.example.jdapresencia.ui.buscador_trabajadores;

import androidx.lifecycle.ViewModel;

import com.example.jdapresencia.model.Registro;
import com.example.jdapresencia.model.User;
import com.example.jdapresencia.MVVMRepository;

import java.util.ArrayList;


public class BuscadorTrabajadoresViewModel extends ViewModel {
    //public BuscadorTrabajadoresViewModel() {
        //mText = new MutableLiveData<>();
        //mText.setValue("Este es el buscador de trabajadores");
    //}

    public ArrayList<User> getUsersByUsername(String username) {
        return MVVMRepository.getUsersByUsername(username);
    }

    public ArrayList<Registro> getListRegistros(String idSession) {
        return MVVMRepository.getListRegistros(idSession);
    }

    //public LiveData<String> getText() {
    //    return mText;
    //}
}