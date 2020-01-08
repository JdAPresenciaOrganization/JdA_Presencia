package com.example.jdapresencia.ui.home_admin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        final WebView webView = root.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

        MiHilo miHilo = new MiHilo();
        miHilo.execute("https://www.google.com");

        homeAdminViewModel.getWeb().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String html) {
                webView.loadData(html,"text/html", "utf-8");
            }
        });

        return root;
    }

    public class MiHilo extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            homeAdminViewModel.downloadURL(strings[0]);
            return null;
        }
    }
}