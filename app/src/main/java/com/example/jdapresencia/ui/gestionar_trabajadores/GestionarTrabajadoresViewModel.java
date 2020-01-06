package com.example.jdapresencia.ui.gestionar_trabajadores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jdapresencia.MVVMRepository;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class GestionarTrabajadoresViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GestionarTrabajadoresViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is gestionar trabajadores fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void addNewWorker(String user, String pass) throws BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchProviderException, InvalidKeyException, InvalidKeySpecException {
        MVVMRepository.addNewWorker(user, pass);
    }

    public void updateWorker(String username, String newUsername, String newPwd, String userRol) throws BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchProviderException, InvalidKeyException, InvalidKeySpecException {
        MVVMRepository.updateWorker(username, newUsername, newPwd, userRol);
    }

    public void deleteWorker(String username) {
        MVVMRepository.deleteWorker(username);
    }
}