package com.example.jdapresencia.ui.buscador_trabajadores;

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
import com.example.jdapresencia.model.User;

import java.util.List;

public class BuscadorTrabajadoresFragment extends Fragment {

    private BuscadorTrabajadoresViewModel buscadorTrabajadoresViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        buscadorTrabajadoresViewModel =
                ViewModelProviders.of(this).get(BuscadorTrabajadoresViewModel.class);
        View root = inflater.inflate(R.layout.fragment_buscador_trabajadores, container, false);

        final TextView textView = root.findViewById(R.id.testfinder);

        buscadorTrabajadoresViewModel.getAllUsersArray().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                textView.setText(users.get(1).getUsername());
            }
        });




        //buscadorTrabajadoresViewModel.getText().observe(this, new Observer<String>() {
        //    @Override
        //    public void onChanged(@Nullable String s) {
        //        textView.setText(s);
        //    }
        //});
        return root;
    }



}