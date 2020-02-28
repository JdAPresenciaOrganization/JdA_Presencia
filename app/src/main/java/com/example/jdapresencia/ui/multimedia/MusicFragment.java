package com.example.jdapresencia.ui.multimedia;

import androidx.lifecycle.ViewModelProviders;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jdapresencia.R;

public class MusicFragment extends Fragment {

    private MusicViewModel musicViewModel;

    Button play, pause;
    MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        musicViewModel =
                ViewModelProviders.of(this).get(MusicViewModel.class);
        View root = inflater.inflate(R.layout.music_fragment, container, false);


        play = root.findViewById(R.id.buttonPlay);
        pause = root.findViewById(R.id.buttonStop);

        //Cargo el fichero a reproducir
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.ahriximaginenocopyrightmusic);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gestiono el Reproductor multimedia
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gestiono el Reproductor multimedia
                mediaPlayer.pause();
            }
        });

        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        //Libero los recursos del reproductor multimedia
        mediaPlayer.release();
    }
}
