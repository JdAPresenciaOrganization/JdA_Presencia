package com.example.jdapresencia.ui.buscador_trabajadores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jdapresencia.R;
import com.example.jdapresencia.model.User;

import java.util.ArrayList;


public class BuscadorTrabajadoresFragment extends Fragment {


    private BuscadorTrabajadoresViewModel buscadorTrabajadoresViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        buscadorTrabajadoresViewModel =
                ViewModelProviders.of(this).get(BuscadorTrabajadoresViewModel.class);
        View root = inflater.inflate(R.layout.fragment_buscador_trabajadores, container, false);

        buscadorTrabajadoresViewModel.pasarContexto(getContext());

        final RecyclerView recyclerViewUser = (RecyclerView) root.findViewById(R.id.recyclerViewUserFinder);

        final EditText query = root.findViewById(R.id.query);

        recyclerViewUser.setLayoutManager(new LinearLayoutManager(getContext()));

        UserAdapter mAdapter = new UserAdapter(buscadorTrabajadoresViewModel.getUsersBy("todos","todos"));
        recyclerViewUser.setAdapter(mAdapter);



        //BOTON

        Button boton_buscar = root.findViewById(R.id.boton_buscar);

        boton_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = query.getText().toString();

                recyclerViewUser.setLayoutManager(new LinearLayoutManager(getContext()));

                UserAdapter mAdapter = new UserAdapter(buscadorTrabajadoresViewModel.getUsersBy("username", string));
                recyclerViewUser.setAdapter(mAdapter);

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


    public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

        private ArrayList<User> usersList;

        public UserAdapter(ArrayList<User> usersList) {
            this.usersList = usersList;
        }

        @Override
        public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.buscador_trabajadores_view_holder, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;

        }



        public void onBindViewHolder(ViewHolder holder, int position) {
            String uid = usersList.get(position).getIdU();
            String username = usersList.get(position).getUsername();
            String rol = usersList.get(position).getRol();

            holder.uid.setText(uid);
            holder.username.setText(username);
            holder.rol.setText(rol);
        }

        public int getItemCount() {
            return usersList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView uid;
            private TextView username;
            private TextView rol;
            public ViewHolder(View v) {
                super(v);
                uid = (TextView) v.findViewById(R.id.view_holder_uid);
                username = (TextView) v.findViewById(R.id.view_holder_username);
                rol = (TextView) v.findViewById(R.id.view_holder_rol);
            }
        }
    }



}