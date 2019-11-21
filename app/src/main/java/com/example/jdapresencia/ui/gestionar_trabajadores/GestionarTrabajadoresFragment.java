package com.example.jdapresencia.ui.gestionar_trabajadores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.jdapresencia.R;

public class GestionarTrabajadoresFragment extends Fragment {

    private GestionarTrabajadoresViewModel gestionarTrabajadoresViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gestionarTrabajadoresViewModel =
                ViewModelProviders.of(this).get(GestionarTrabajadoresViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gestionar_trabajadores, container, false);
        final TextView textView = root.findViewById(R.id.text_gestionar_trabajadores);
        gestionarTrabajadoresViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}