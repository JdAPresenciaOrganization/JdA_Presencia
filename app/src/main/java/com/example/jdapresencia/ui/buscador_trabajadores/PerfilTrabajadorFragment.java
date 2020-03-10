package com.example.jdapresencia.ui.buscador_trabajadores;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jdapresencia.R;

public class PerfilTrabajadorFragment extends Fragment {

    private BuscadorTrabajadoresViewModel perfilTrabajadorViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        perfilTrabajadorViewModel =
                ViewModelProviders.of(this).get(BuscadorTrabajadoresViewModel.class);
        View root = inflater.inflate(R.layout.fragment_perfil_trabajador, container, false);

        return root;
    }
}
