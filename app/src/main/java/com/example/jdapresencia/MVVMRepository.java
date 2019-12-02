package com.example.jdapresencia;

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MVVMRepository {

    public static String FILE_NAME = "/userRegisterFile.dat";
    private Context context;
    //Singleton
    private static MVVMRepository srepository;

    private MVVMRepository(Context context){
        this.context = context;
    }
    public static MVVMRepository get(Context context){
        if (srepository == null){
            srepository = new MVVMRepository(context);
        }
        return srepository;
    }

    public static void userCheckIn() {
        Log.i("@@@@@@@@@@@@@@@@@@@@@", "check IN");
        Log.i("@@@@@@@@@@@@@@@@@@@@@", fechaActual());
    }

    public static void userCheckOut() {
        Log.i("@@@@@@@@@@@@@@@@@@@@@", "check OUT");
        Log.i("@@@@@@@@@@@@@@@@@@@@@", fechaActual());
    }

    /**
     * Devuelve la fecha en formato dd/MM/yyyy HH:mm:ss
     * @return
     */
    public static String fechaActual(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        Date date = new Date();

        return dateFormat.format(date);
    }
}
