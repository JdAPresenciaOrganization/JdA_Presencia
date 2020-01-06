package com.example.jdapresencia.ui.gestionar_trabajadores;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.jdapresencia.R;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ModificarTrabajadoresFragment extends Fragment {

    private GestionarTrabajadoresViewModel modificarTrabajadorViewModel;
    EditText usernameToModify, usernameUpdate, pwdUpdate;
    Button bUpdateWorker;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        modificarTrabajadorViewModel =
                ViewModelProviders.of(this).get(GestionarTrabajadoresViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_modificar_trabajadores, container, false);

        usernameToModify = root.findViewById(R.id.usernameModify);
        usernameUpdate = root.findViewById(R.id.usernameModifyUpdate);
        pwdUpdate = root.findViewById(R.id.passwordModifyUpdate);
        radioGroup = root.findViewById(R.id.radioGroupUserRol);
        bUpdateWorker = root.findViewById(R.id.button_userModify);

        bUpdateWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = root.findViewById(selectedId);

                try {
                    modificarTrabajadorViewModel.updateWorker(usernameToModify.getText().toString(),
                            usernameUpdate.getText().toString(), pwdUpdate.getText().toString(), radioButton.getText().toString());
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }
}

