package com.example.jdapresencia.ui.gestionar_trabajadores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.jdapresencia.R;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class GestionarTrabajadoresFragment extends Fragment {

    private GestionarTrabajadoresViewModel gestionarTrabajadoresViewModel;
    EditText newUser, pass;
    Button bNewRegister, bModifyWorker, bDeleteUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gestionarTrabajadoresViewModel =
                ViewModelProviders.of(this).get(GestionarTrabajadoresViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gestionar_trabajadores, container, false);

        newUser = root.findViewById(R.id.usernameNewUser);
        pass = root.findViewById(R.id.passwordNewUser);
        bNewRegister = root.findViewById(R.id.button_addNewUser);
        bModifyWorker = root.findViewById(R.id.button_modifyUser);
        bDeleteUser = root.findViewById(R.id.button_deleteUser);

        bNewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    gestionarTrabajadoresViewModel.addNewWorker(newUser.getText().toString(), pass.getText().toString());
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

        bModifyWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModificarTrabajadoresFragment fragment = new ModificarTrabajadoresFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        bDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EliminarTrabajadoresFragment fragment = new EliminarTrabajadoresFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        /*
        final TextView textView = root.findViewById(R.id.text_gestionar_trabajadores);
        gestionarTrabajadoresViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
         */
        return root;
    }
}