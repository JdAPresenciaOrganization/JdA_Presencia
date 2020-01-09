package com.example.jdapresencia.ui.home_admin;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HomeAdminViewModel extends ViewModel {

    //private MutableLiveData<String> mText;
    private MutableLiveData<String> mWeb;

    public HomeAdminViewModel() {
        mWeb = new MutableLiveData<>();
        //mText = new MutableLiveData<>();
        //mText.setValue("This is HOME ADMIN fragment");
    }

    public MutableLiveData<String> getWeb() {
        return mWeb;
    }

    /*
    public LiveData<String> getText() {
        return mText;
    }
     */

    public void downloadURL(String web){
        HttpsURLConnection connection;
        URL url;
        StringBuilder result = new StringBuilder();
        //result ="";

        try {
            url = new URL(web);
            connection = (HttpsURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            /*
            InputStream inputStream = connection.getInputStream();

            int data = inputStream.read();

            while(data != -1){
                result += (char) data;
                data = inputStream.read();
            }
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("RESULT", result.toString());
        mWeb.postValue(result.toString());
    }
}