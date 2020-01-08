package com.example.jdapresencia.ui.mis_registros;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jdapresencia.MVVMRepository;
import com.example.jdapresencia.R;
import com.example.jdapresencia.model.Registro;

import java.text.ParseException;
import java.util.ArrayList;

public class RegistrosFragment extends Fragment {

    private RegistrosViewModel registrosViewModel;
    private RecyclerView recycler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registrosViewModel =
                ViewModelProviders.of(this).get(RegistrosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registros, container, false);

        //Captura el id del usuario logueado enviado del MainActivity
        SharedPreferences pref = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        final String idSession = pref.getString("sessionUserId_key", "");

        //Asignar que Adapter le va a pasar la información
        recycler = root.findViewById(R.id.recycler_misRegistros);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        AdapterRegistros adapter = new AdapterRegistros(getActivity(), registrosViewModel.getListRegistros(idSession));
        recycler.setAdapter(adapter);

        final TextView textView = root.findViewById(R.id.text_registros);
        registrosViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    public static class AdapterRegistros extends RecyclerView.Adapter<AdapterRegistros.ViewHolderRegistros> {

        Context context;
        ArrayList<Registro> listRegistros;

        public AdapterRegistros(Context context, ArrayList<Registro> listRegistros) {
            this.context = context;
            this.listRegistros = listRegistros;
        }

        @NonNull
        @Override
        public ViewHolderRegistros onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_mis_registros, null, false);
            return new ViewHolderRegistros(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderRegistros holder, int position) {
            Registro registro = listRegistros.get(position);
            holder.tvFecha.setText(registro.getFecha());
            holder.tvHoraEntrada.setText(registro.getHoraEntrada());
            holder.tvHoraSalida.setText(registro.getHoraSalida());
            try {
                holder.tvDuracion.setText(MVVMRepository.totalDayHours(registro.getHoraEntrada(), registro.getHoraSalida()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return listRegistros.size();
        }

        public class ViewHolderRegistros extends RecyclerView.ViewHolder {
            TextView tvFecha, tvHoraEntrada, tvHoraSalida, tvDuracion;

            public ViewHolderRegistros(@NonNull View itemView) {
                super(itemView);

                tvFecha = itemView.findViewById(R.id.idDatoFecha);
                tvHoraEntrada = itemView.findViewById(R.id.idDatoHoraEntrada);
                tvHoraSalida = itemView.findViewById(R.id.idDatoHoraSalida);
                tvDuracion = itemView.findViewById(R.id.idDatoDuracion);

            }
        }
    }
}