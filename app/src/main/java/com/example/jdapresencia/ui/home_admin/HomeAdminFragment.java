package com.example.jdapresencia.ui.home_admin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.jdapresencia.R;

public class HomeAdminFragment extends Fragment {

    private HomeAdminViewModel homeAdminViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeAdminViewModel =
                ViewModelProviders.of(this).get(HomeAdminViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home_admin, container, false);
        /*
        final TextView textView = root.findViewById(R.id.text_home_admin);
        homeAdminViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
         */
        //Variable de tipo WebView
        final WebView webView = root.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

        //Se instancia la clase y se pasa la URL de la página
        MiHilo miHilo = new MiHilo();
        miHilo.execute("https://agora.xtec.cat/insjoandaustria/");

        homeAdminViewModel.getWeb().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String html) {
                webView.loadData(html,"text/html; charset=utf-8", "utf-8");
            }
        });

        return root;
    }

    //Clase que extiende AsyncTask que escucha la respuesta de downloadURL
    public class MiHilo extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            homeAdminViewModel.downloadURL(strings[0]);
            return null;
        }
    }
}