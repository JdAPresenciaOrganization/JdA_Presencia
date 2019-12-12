package com.example.jdapresencia.ui.buscador_trabajadores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jdapresencia.R;
import com.example.jdapresencia.model.User;
import com.example.jdapresencia.ui.buscador_trabajadores.registros_trabajador.RegistrosTrabajadorFragment;

import java.util.ArrayList;


public class BuscadorTrabajadoresFragment extends Fragment {


    private BuscadorTrabajadoresViewModel buscadorTrabajadoresViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        buscadorTrabajadoresViewModel =
                ViewModelProviders.of(this).get(BuscadorTrabajadoresViewModel.class);
        View root = inflater.inflate(R.layout.fragment_buscador_trabajadores, container, false);

        final RecyclerView recyclerViewUser = (RecyclerView) root.findViewById(R.id.recyclerViewUserFinder);
        final EditText query = root.findViewById(R.id.query);

        //Cargamos RecyclerView de todos los usuarios en cuanto entremos a este fragment

        recyclerViewUser.setLayoutManager(new LinearLayoutManager(getContext()));

        UserAdapter mAdapter = new UserAdapter(buscadorTrabajadoresViewModel.getUsersBy("todos los usuarios", "todos"), new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(String uid_user) {
                Toast.makeText(getContext(), uid_user, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewUser.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewUser.getContext(),((LinearLayoutManager) recyclerViewUser.getLayoutManager()).getOrientation());
        recyclerViewUser.addItemDecoration(dividerItemDecoration);


        //BOTON BUSCAR

        Button boton_buscar = root.findViewById(R.id.boton_buscar);

        boton_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = query.getText().toString();

                recyclerViewUser.setLayoutManager(new LinearLayoutManager(getContext()));

                UserAdapter mAdapter = new UserAdapter(buscadorTrabajadoresViewModel.getUsersBy("username", string), new RecyclerViewOnItemClickListener() {
                    @Override
                    public void onClick(String uid_user) {
                        Toast.makeText(getContext(), uid_user, Toast.LENGTH_SHORT).show();
                    }
                });
                recyclerViewUser.setAdapter(mAdapter);

                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewUser.getContext(),((LinearLayoutManager) recyclerViewUser.getLayoutManager()).getOrientation());
                recyclerViewUser.addItemDecoration(dividerItemDecoration);
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
        private RecyclerViewOnItemClickListener listener;
        private Button boton_registros;

        public UserAdapter(@NonNull ArrayList<User> usersList, @NonNull RecyclerViewOnItemClickListener listener) {
            this.usersList = usersList;
            this.listener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_buscador_trabajadores, parent, false);
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
            holder.bind(uid, listener);
            if (rol.equals("trabajador")) {
                boton_registros = (Button) holder.itemView.findViewById(R.id.view_holder_boton);
                boton_registros.setVisibility(View.VISIBLE);
            }
        }

        public int getItemCount() {
            return usersList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private Bundle uid_para_fragment_registros = new Bundle();
            private TextView uid;
            private TextView username;
            private TextView rol;
            private Button boton_ver_registros;
            public ViewHolder(View v) {
                super(v);
                uid = (TextView) v.findViewById(R.id.view_holder_uid);
                username = (TextView) v.findViewById(R.id.view_holder_username);
                rol = (TextView) v.findViewById(R.id.view_holder_rol);
                boton_ver_registros = (Button) v.findViewById(R.id.view_holder_boton);
                boton_ver_registros.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uid_para_fragment_registros.putString("uid", usersList.get(getAdapterPosition()).getIdU());
                        RegistrosTrabajadorFragment fragment = new RegistrosTrabajadorFragment();
                        fragment.setArguments(uid_para_fragment_registros);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.nav_host_fragment,fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                });
            }

            public void bind(final String uid_user, final RecyclerViewOnItemClickListener listener) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        listener.onClick(uid_user);

                    }

                });
            }
        }
    }



}