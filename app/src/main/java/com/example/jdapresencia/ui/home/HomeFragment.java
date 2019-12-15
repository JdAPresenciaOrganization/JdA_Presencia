package com.example.jdapresencia.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.jdapresencia.R;

import java.io.IOException;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button bCheckIn, bCheckOut;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Captura el id del usuario logueado enviado del MainActivity
        SharedPreferences pref = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        final String idSession = pref.getString("sessionUserId_key", "");

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Captura de botón en variables
        bCheckIn = root.findViewById(R.id.buttonCheckIn);
        bCheckOut = root.findViewById(R.id.buttonCheckOut);

        bCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    homeViewModel.userCheckIn(idSession);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        bCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    homeViewModel.userCheckOut(idSession);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}