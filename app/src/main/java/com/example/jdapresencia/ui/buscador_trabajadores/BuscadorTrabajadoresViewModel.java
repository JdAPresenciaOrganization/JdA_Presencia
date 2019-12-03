package com.example.jdapresencia.ui.buscador_trabajadores;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import com.example.jdapresencia.model.User;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class BuscadorTrabajadoresViewModel extends ViewModel {

    private Context context;

    //private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<User>> ListaMutableAllUsers;

    public BuscadorTrabajadoresViewModel() {
        //mText = new MutableLiveData<>();
        //mText.setValue("Este es el buscador de trabajadores");



    }

    public void pasarContexto(Context context) {
        this.context = context;

    }

    public ArrayList<User> getUsersBy( String campo, String valor) {

        ArrayList<User> usersArray = new ArrayList<>();

        try {
            String FILE_NAME = "/usersFile.dat";

            File file = new File(context.getFilesDir().getPath()+FILE_NAME);
            FileInputStream filein = new FileInputStream(file);
            ObjectInputStream entrada = new ObjectInputStream(filein);

            User usuario = (User) entrada.readObject();

            while (usuario!=null) {

                switch (campo) {
                    case "idU":
                        if (usuario.getIdU().equals(valor)) {
                            usersArray.add(usuario);
                        }
                        break;
                    case "rol":
                        if (usuario.getRol().equals(valor)) {
                            usersArray.add(usuario);
                        }
                        break;
                    case "username":
                        if (usuario.getUsername().equals(valor)) {
                            usersArray.add(usuario);
                        }
                        break;
                    default:
                        usersArray.add(usuario);
                }

                usuario = (User) entrada.readObject();

            }
            entrada.close();
        } catch (IndexOutOfBoundsException | NullPointerException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return usersArray;
    };

    //public LiveData<String> getText() {
    //    return mText;
    //}
}