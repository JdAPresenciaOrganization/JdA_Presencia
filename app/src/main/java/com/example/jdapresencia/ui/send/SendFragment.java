package com.example.jdapresencia.ui.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.jdapresencia.R;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;
    EditText sendMsg;
    Button btnSendMsg;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        final TextView textView = root.findViewById(R.id.text_send);

        sendMsg = root.findViewById(R.id.sendMsg);
        btnSendMsg = root.findViewById(R.id.btn_sendMsg);

        sendViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sendMsg.getText().toString().isEmpty()) {
                    sendViewModel.WriteMsgFirebase(sendMsg.getText().toString());
                } else {
                    Toast.makeText(getContext(), "Insert message", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }
}