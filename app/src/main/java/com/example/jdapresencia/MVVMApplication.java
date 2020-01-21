package com.example.jdapresencia;

import android.app.Application;

public class MVVMApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        MVVMRepository.get(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        MVVMRepository.closeDatabase();
        MVVMRepository.closeDatabaseInstance();
    }
}
