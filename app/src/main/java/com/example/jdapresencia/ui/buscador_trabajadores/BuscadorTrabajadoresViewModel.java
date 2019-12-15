package com.example.jdapresencia.ui.buscador_trabajadores;

import androidx.lifecycle.ViewModel;

import com.example.jdapresencia.model.User;
import com.example.jdapresencia.MVVMRepository;

import java.util.ArrayList;


public class BuscadorTrabajadoresViewModel extends ViewModel {
    //public BuscadorTrabajadoresViewModel() {
        //mText = new MutableLiveData<>();
        //mText.setValue("Este es el buscador de trabajadores");
    //}

    //public void pasarContexto(Context context) {
    //    this.context = context;
    //    }

    public ArrayList<User> getUsersBy( String campo, String valor) {

        return MVVMRepository.getUsersBy(campo, valor);
    };

    //public LiveData<String> getText() {
    //    return mText;
    //}
}