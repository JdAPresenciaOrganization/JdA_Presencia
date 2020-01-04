package com.example.jdapresencia.ui.gestionar_trabajadores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class ModificarTrabajadoresFragment extends Fragment {

    private GestionarTrabajadoresViewModel modificarTrabajadorViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        modificarTrabajadorViewModel =
                ViewModelProviders.of(this).get(GestionarTrabajadoresViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registros_trabajador, container, false);

        return root;
    }
}
