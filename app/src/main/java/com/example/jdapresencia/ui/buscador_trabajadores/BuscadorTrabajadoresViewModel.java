package com.example.jdapresencia.ui.buscador_trabajadores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jdapresencia.model.User;


import java.util.ArrayList;

public class BuscadorTrabajadoresViewModel extends ViewModel {

    //private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<User>> ListaMutableAllUsers;

    public BuscadorTrabajadoresViewModel() {
        //mText = new MutableLiveData<>();
        //mText.setValue("Este es el buscador de trabajadores");
        ListaMutableAllUsers = new MutableLiveData<>();

        User user1 = new User("1", "admin", "admin", "admin");
        User user2 = new User("2", "trabajador", "employee", "1234");

        ArrayList<User> ListaAllUsers = new ArrayList<>();
        ListaAllUsers.add(user1);
        ListaAllUsers.add(user2);
        ListaMutableAllUsers.setValue(ListaAllUsers);


    }

    public LiveData<ArrayList<User>> getUsersBy(String campo, String valor) {

        MutableLiveData<ArrayList<User>> usersArray = new MutableLiveData<>();

        return usersArray;
    };

    public LiveData<ArrayList<User>> getAllUsersArray() {return ListaMutableAllUsers;}
    //public LiveData<String> getText() {
    //    return mText;
    //}
}