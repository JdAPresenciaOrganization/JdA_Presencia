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

public class Registros_trabajador extends Fragment {

    private RegistrosTrabajadorViewModel mViewModel;

    public static Registros_trabajador newInstance() {
        return new Registros_trabajador();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registros_trabajador, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegistrosTrabajadorViewModel.class);
        // TODO: Use the ViewModel
    }

}
