package com.example.jdapresencia.ui.gestionar_trabajadores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.jdapresencia.R;

public class GestionarTrabajadoresFragment extends Fragment {

    private GestionarTrabajadoresViewModel gestionarTrabajadoresViewModel;
    EditText newUser, pass;
    Button bNewRegister, bModifyWorker;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gestionarTrabajadoresViewModel =
                ViewModelProviders.of(this).get(GestionarTrabajadoresViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gestionar_trabajadores, container, false);

        newUser = root.findViewById(R.id.usernameNewUser);
        pass = root.findViewById(R.id.passwordNewUser);
        bNewRegister = root.findViewById(R.id.button_addNewUser);
        bModifyWorker = root.findViewById(R.id.button_modifyUser);

        bNewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gestionarTrabajadoresViewModel.addNewWorker(newUser.getText().toString(), pass.getText().toString());
            }
        });

        bModifyWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModificarTrabajadoresFragment fragment = new ModificarTrabajadoresFragment();

                Navigation.findNavController(v).navigate(R.id.nav_registros_trabajadores);
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