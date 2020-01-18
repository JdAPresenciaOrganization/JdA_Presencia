package com.example.jdapresencia.ui.send;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jdapresencia.model.Mensaje;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SendViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SendViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void WriteMsgFirebase(String msg) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference msg_reference = reference.child("JdAP").child("mensajes").push();
        msg_reference.setValue(new Mensaje(1, "yo", "tu",
                "holatest", 1));
        reference.child("JdAP").child("usuarios").child("tu").child("mensaje")
                .setValue(msg_reference.getKey());
    }
}