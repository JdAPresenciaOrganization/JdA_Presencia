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

        RegisterAdapter adapter = new RegisterAdapter(registrosTrabajadorViewModel.getListRegistros(String.valueOf(uid)));
        recycler.setAdapter(adapter);

        return root;
    }

    public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.ViewHolder> {

        private ArrayList<Registro> listRegistros;

        public RegisterAdapter(ArrayList<Registro> listRegistros){
            this.listRegistros = listRegistros;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_registros_trabajador, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Registro registro = listRegistros.get(position);

            holder.fecha.setText(registro.getFecha());
            holder.entrada.setText(registro.getHoraEntrada());
            holder.salida.setText(registro.getHoraSalida());
            //holder.duracion.setText(registrosTrabajadorViewModel.RestDates(entrada, salida));
        }

        @Override
        public int getItemCount() {
            return listRegistros.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView fecha;
            private TextView entrada;
            private TextView salida;
            private TextView duracion;

            public ViewHolder(@NonNull View v) {
                super(v);

                fecha = v.findViewById(R.id.fecha);
                entrada = v.findViewById(R.id.entrada);
                salida = v.findViewById(R.id.salida);
                duracion = v.findViewById(R.id.duracion);
            }
        }
    }
}