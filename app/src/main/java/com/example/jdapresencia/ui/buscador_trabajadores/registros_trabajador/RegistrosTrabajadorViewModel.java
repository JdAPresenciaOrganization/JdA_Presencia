package com.example.jdapresencia.ui.buscador_trabajadores.registros_trabajador;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.jdapresencia.MVVMRepository;
import com.example.jdapresencia.model.Registro;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class RegistrosTrabajadorViewModel extends ViewModel {


    public ArrayList<Registro> getRegisters(String uid) {
       return MVVMRepository.getRegisters(uid);
    }

    public String RestDates(String fecha1, String fecha2) {
        return MVVMRepository.RestDates(fecha1, fecha2);
    }

}
