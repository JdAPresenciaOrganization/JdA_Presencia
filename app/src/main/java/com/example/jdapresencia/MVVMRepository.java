package com.example.jdapresencia;

import android.content.Context;

public class MVVMRepository {

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
}
