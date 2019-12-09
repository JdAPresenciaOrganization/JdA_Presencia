package com.example.jdapresencia.ui.buscador_trabajadores.registros_trabajador;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jdapresencia.R;

public class RegistrosTrabajadorFragment extends Fragment {

    private RegistrosTrabajadorViewModel registrosTrabajadorViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        registrosTrabajadorViewModel =
                ViewModelProviders.of(this).get(RegistrosTrabajadorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registros_trabajador, container, false);

        return root;
    }

}
