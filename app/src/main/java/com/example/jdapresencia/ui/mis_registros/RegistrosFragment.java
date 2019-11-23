package com.example.jdapresencia.ui.mis_registros;

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

public class RegistrosFragment extends Fragment {

    private RegistrosViewModel registrosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registrosViewModel =
                ViewModelProviders.of(this).get(RegistrosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registros, container, false);
        final TextView textView = root.findViewById(R.id.text_registros);
        registrosViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}