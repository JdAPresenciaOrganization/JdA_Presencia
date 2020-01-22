package com.example.jdapresencia.ui.receive;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jdapresencia.R;
import com.example.jdapresencia.model.Mensaje;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ReceiveFragment extends Fragment {

    private ReceiveViewModel receiveViewModel;

    private RecyclerView recyclerView;
    private AdapterChat adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        receiveViewModel =
                ViewModelProviders.of(this).get(ReceiveViewModel.class);
        View root = inflater.inflate(R.layout.fragment_receive, container, false);

        recyclerView = root.findViewById(R.id.recyclerListMessages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("JdAP")
                .child("mensajes");

        FirebaseRecyclerOptions<Mensaje> options =
                new FirebaseRecyclerOptions.Builder<Mensaje>()
                        .setQuery(query, Mensaje.class)
                        .build();

        adapter = new AdapterChat(options);

        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public class AdapterChat extends FirebaseRecyclerAdapter<Mensaje, AdapterChat.ChatViewHolder> {
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public AdapterChat(@NonNull FirebaseRecyclerOptions<Mensaje> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull AdapterChat.ChatViewHolder holder, int position, @NonNull Mensaje model) {
            // Bind the Chat object to the ChatHolder
            holder.messageText.setText(model.getMensaje());
            holder.messageSender.setText(model.getEmisor());
            holder.messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
        }

        @NonNull
        @Override
        public AdapterChat.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Create a new instance of the ViewHolder, in this case we are using a custom
            // layout called R.layout.message for each item
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_mensajes, parent,false);
            return new ChatViewHolder(view);
        }

        public class ChatViewHolder extends RecyclerView.ViewHolder {
            TextView messageText, messageSender, messageTime;

            public ChatViewHolder(@NonNull View itemView) {
                super(itemView);

                messageText = itemView.findViewById(R.id.message_text);
                messageSender = itemView.findViewById(R.id.message_sender);
                messageTime = itemView.findViewById(R.id.message_time);

            }
        }
    }
}
