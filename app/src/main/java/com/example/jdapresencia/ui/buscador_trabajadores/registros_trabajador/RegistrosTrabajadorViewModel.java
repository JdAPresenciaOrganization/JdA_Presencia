package com.example.jdapresencia.ui.buscador_trabajadores.registros_trabajador;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.jdapresencia.model.Registro;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class RegistrosTrabajadorViewModel extends ViewModel {

    private Context context;

    public void pasarContexto(Context context) {
        this.context = context;

    }

    public ArrayList<Registro> getRegisters(String uid) {
        ArrayList<Registro> registros = new ArrayList<>();
        String FILE_NAME = "/" + uid + ".dat";

        File file = new File(context.getFilesDir().getPath()+FILE_NAME);

        try {
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(file));
            registros = (ArrayList<Registro>) entrada.readObject();
            entrada.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return registros;
    }

}
