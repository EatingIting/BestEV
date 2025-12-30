package com.ysc.bestev.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ysc.bestev.R;

public class GalleryFragment extends Fragment {

    Button bolt,bongo3,eqc,etron,i3,iface,ioniq,kona,model3,models,modelx,modely,niro,porter2,ray,sm3ze,soul,twizy;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        getActivity().setTitle("전기차 정보(종류)");
        bolt = (Button)root.findViewById(R.id.bolt);
        bongo3 = (Button)root.findViewById(R.id.bongo3);
        eqc = (Button)root.findViewById(R.id.eqc);
        etron = (Button)root.findViewById(R.id.etron);
        i3 = (Button)root.findViewById(R.id.i3);
        iface = (Button)root.findViewById(R.id.iface);
        ioniq = (Button)root.findViewById(R.id.ioniq);
        kona = (Button)root.findViewById(R.id.kona);
        model3 = (Button)root.findViewById(R.id.model3);
        models = (Button)root.findViewById(R.id.models);
        modelx = (Button)root.findViewById(R.id.modelx);
        modely = (Button)root.findViewById(R.id.modely);
        niro = (Button)root.findViewById(R.id.niro);
        porter2 = (Button)root.findViewById(R.id.porter2);
        ray = (Button)root.findViewById(R.id.ray);
        sm3ze = (Button)root.findViewById(R.id.sm3ze);
        soul = (Button)root.findViewById(R.id.soul);
        twizy = (Button)root.findViewById(R.id.twizy);

        bolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return root;
    }
}