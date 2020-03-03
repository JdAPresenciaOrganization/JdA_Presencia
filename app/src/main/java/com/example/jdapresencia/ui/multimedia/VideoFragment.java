package com.example.jdapresencia.ui.multimedia;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.jdapresencia.R;

public class VideoFragment extends Fragment {

    private VideoViewModel videoViewModel;

    VideoView miVideoView;
    MediaController mediaController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        videoViewModel =
                ViewModelProviders.of(this).get(VideoViewModel.class);
        View root = inflater.inflate(R.layout.video_fragment, container, false);

        miVideoView = root.findViewById(R.id.videoView);

        //Indico que fichero multimedia quiero cargar formando el path con la sintaxis adecuada
        miVideoView.setVideoPath("android.resource://"+ getActivity().getPackageName()+"/"+R.raw.wonderlightsaurorasborealesenislandia);

        //Inicializar MediaController
        mediaController = new MediaController(getContext());

        //Asignar MediaController al VideoView
        miVideoView.setMediaController(mediaController);

        //Fijar el MediaController a la vista del VideoView
        mediaController.setAnchorView(miVideoView);

        //Permito que el MediaController gestione el Player
        mediaController.setMediaPlayer(miVideoView);

        return root;
    }
}
