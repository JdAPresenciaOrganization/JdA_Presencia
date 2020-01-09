package com.example.jdapresencia.ui.gestionar_trabajadores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jdapresencia.MVVMRepository;

public class GestionarTrabajadoresViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GestionarTrabajadoresViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is gestionar trabajadores fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void addNewWorker(String user, String pass) {
        MVVMRepository.addNewWorker(user, pass);
    }

    public void updateWorker(String username, String newUsername, String newPwd, String userRol) {
        MVVMRepository.updateWorker(username, newUsername, newPwd, userRol);
    }

    public void deleteWorker(String username) {
        MVVMRepository.deleteWorker(username);
    }
}