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

import com.example.jdapresencia.R;

public class EliminarTrabajadoresFragment extends Fragment {

    private GestionarTrabajadoresViewModel eliminarTrabajadorViewModel;
    EditText usernameToDelete;
    Button bDeleteUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        eliminarTrabajadorViewModel =
                ViewModelProviders.of(this).get(GestionarTrabajadoresViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_eliminar_trabajadores, container, false);

        usernameToDelete = root.findViewById(R.id.usernameDeleteUser);
        bDeleteUser = root.findViewById(R.id.button_deleteUser);

        bDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarTrabajadorViewModel.deleteWorker(usernameToDelete.getText().toString());
            }
        });

        return root;
    }
}
