package com.example.jdapresencia.ui.buscador_trabajadores;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;

import com.example.jdapresencia.LoginActivity;
import com.example.jdapresencia.model.LoginPresenterImpl;
import com.example.jdapresencia.model.User;
import com.example.jdapresencia.presenter.LoginPresenter;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static com.example.jdapresencia.LoginActivity.FILE_NAME;

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

        File file = new File(context.getFilesDir().getPath()+FILE_NAME);
        ArrayList<User> allusers = new ArrayList<>();
        ArrayList<User> customUsersArray = new ArrayList<>();



        try {
            String FILE_NAME = "/usersFile.dat";
            File file = new File(context.getFilesDir().getPath()+FILE_NAME);
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(file));
            User usuario = (User) entrada.readObject();

                 switch (campo) {
                     case "idU":
                         if (allusers.get(i).getIdU().equals(valor)) {
                             customUsersArray.add(allusers.get(i));
                         }
                         break;
                     case "rol":
                         if (allusers.get(i).getRol().equals(valor)) {
                             customUsersArray.add(allusers.get(i));
                         }
                         break;
                     case "username":
                         if (allusers.get(i).getUsername().equals(valor)) {
                             customUsersArray.add(allusers.get(i));
                         }
                         break;
                     default:
                         customUsersArray = allusers;
                 }
             }

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
        } catch (IndexOutOfBoundsException | NullPointerException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ignored) {

        }

        return customUsersArray;
    };

    //public LiveData<String> getText() {
    //    return mText;
    //}
}