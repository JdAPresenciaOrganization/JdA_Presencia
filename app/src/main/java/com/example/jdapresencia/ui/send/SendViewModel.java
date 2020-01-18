package com.example.jdapresencia.ui.send;

import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jdapresencia.MVVMRepository;

public class SendViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SendViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void WriteMsgFirebase(EditText msg, String idSession) {
        MVVMRepository.WriteMsgFirebase(msg, idSession);
    }
}