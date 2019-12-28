package com.example.jdapresencia.ui.buscador_trabajadores;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jdapresencia.R;
import com.example.jdapresencia.model.Registro;
import com.example.jdapresencia.ui.mis_registros.RegistrosFragment;

import java.util.ArrayList;

public class RegistrosTrabajadorFragment extends Fragment {

    private BuscadorTrabajadoresViewModel registrosTrabajadorViewModel;
    private RecyclerView recycler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        registrosTrabajadorViewModel =
                ViewModelProviders.of(this).get(BuscadorTrabajadoresViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registros_trabajador, container, false);

        // Inflate the layout for this fragment
        Bundle bundle = this.getArguments();
        int uid = bundle.getInt("uid_key");

        recycler = root.findViewById(R.id.recyclerViewRegistros);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        //Se aprovecha el adapter de los registros personales del trabajador
        RegistrosFragment.AdapterRegistros adapter =
                new RegistrosFragment.AdapterRegistros(getActivity(), registrosTrabajadorViewModel.getListRegistros(String.valueOf(uid)));
        recycler.setAdapter(adapter);

        return root;
    }
}