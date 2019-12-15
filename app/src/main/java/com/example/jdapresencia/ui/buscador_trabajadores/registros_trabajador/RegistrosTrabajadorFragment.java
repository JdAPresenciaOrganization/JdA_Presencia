package com.example.jdapresencia.ui.buscador_trabajadores.registros_trabajador;

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

    private RegistrosTrabajadorViewModel registrosTrabajadorViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        registrosTrabajadorViewModel =
                ViewModelProviders.of(this).get(RegistrosTrabajadorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registros_trabajador, container, false);

        Bundle uid_recuperado = getArguments();
        String uid_empleado = uid_recuperado.getString("uid");


        RecyclerView recyclerViewRegistros = (RecyclerView) root.findViewById(R.id.recyclerViewRegistros);

        recyclerViewRegistros.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewRegistros.setAdapter(new RegisterAdapter(registrosTrabajadorViewModel.getRegisters(uid_empleado)));

        return root;
    }

    public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.ViewHolder> {

        private ArrayList<Registro> registros;

        public RegisterAdapter(ArrayList<Registro> registros){
            this.registros = registros;
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
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_registros_trabajador, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            String fecha = registros.get(position).getFecha();
            String entrada = registros.get(position).getHoraEntrada();
            String salida = registros.get(position).getHoraSalida();

            holder.fecha.setText(fecha);
            holder.entrada.setText(entrada);
            holder.salida.setText(salida);
            holder.duracion.setText(registrosTrabajadorViewModel.RestDates(entrada, salida));

        }

        @Override
        public int getItemCount() {
            return registros.size();
        }
    }

}
