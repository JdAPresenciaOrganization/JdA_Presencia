package com.example.jdapresencia.ui.buscador_trabajadores;

import android.content.Context;
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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jdapresencia.R;
import com.example.jdapresencia.model.User;

import java.util.ArrayList;

public class BuscadorTrabajadoresFragment extends Fragment {

    private BuscadorTrabajadoresViewModel buscadorTrabajadoresViewModel;
    private RecyclerView recycler;
    Button boton_buscar;
    EditText userToFind;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        buscadorTrabajadoresViewModel =
                ViewModelProviders.of(this).get(BuscadorTrabajadoresViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_buscador_trabajadores, container, false);

        recycler = root.findViewById(R.id.recyclerViewUserFinder);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        //Se carga una lista con todos los trabajadores al entrar en el fragment
        UserAdapter mAdapter = new UserAdapter(getActivity(), buscadorTrabajadoresViewModel.getUsersByUsername(""));
        recycler.setAdapter(mAdapter);

        //Boton buscador
        boton_buscar = root.findViewById(R.id.boton_buscar);

        //Buscador por nombre (username) del trabajador
        boton_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userToFind = root.findViewById(R.id.input_buscador);

                UserAdapter mAdapter = new UserAdapter(getActivity(),
                        buscadorTrabajadoresViewModel.getUsersByUsername(userToFind.getText().toString()));
                recycler.setAdapter(mAdapter);
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

        Context context;
        private ArrayList<User> usersList;
        private Button boton_registros;

        public UserAdapter(Context context, ArrayList<User> usersList) {
            this.context = context;
            this.usersList = usersList;
        }

        @Override
        public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_buscador_trabajadores, parent, false);
            return new ViewHolder(v);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            User user = usersList.get(position);
            holder.uid.setText(String.valueOf(user.getIdU()));
            holder.username.setText(user.getUsername());
            holder.rol.setText(user.getRol());

            if (user.getRol().equals("trabajador")) {
                boton_registros = holder.itemView.findViewById(R.id.view_holder_boton);
                boton_registros.setVisibility(View.VISIBLE);
            }
        }

        public int getItemCount() {
            return usersList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView uid;
            private TextView username;
            private TextView rol;
            private Button boton_ver_registros;

            public ViewHolder(View v) {
                super(v);

                uid = v.findViewById(R.id.view_holder_uid);
                username = v.findViewById(R.id.view_holder_username);
                rol = v.findViewById(R.id.view_holder_rol);
                boton_ver_registros = v.findViewById(R.id.view_holder_boton);

                boton_ver_registros.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RegistrosTrabajadorFragment fragment = new RegistrosTrabajadorFragment();

                        Bundle bundle = new Bundle();
                        bundle.putInt("uid_key", usersList.get(getAdapterPosition()).getIdU());

                        fragment.setArguments(bundle);
                        //Se crea un nuevo fragment en mobile_navigation con el layout correspondiente de la clase detalle
                        Navigation.findNavController(itemView).navigate(R.id.nav_registros_trabajadores, bundle);
                    }
                });
            }
        }
    }
}