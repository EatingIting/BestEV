package com.ysc.bestev.ui.slideshow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ysc.bestev.Data.UserInformationViewModel;
import com.ysc.bestev.R;

public class SlideshowFragment extends Fragment {
    UserInformationViewModel userViewModel;
    Button Activity, License;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserInformationViewModel.class);

        Activity = (Button)root.findViewById(R.id.Activity);
        License = (Button)root.findViewById(R.id.License);

        Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://navermaps.github.io/android-map-sdk/reference/com/naver/maps/map/app/LegalNoticeActivity.html";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        License.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://navermaps.github.io/android-map-sdk/reference/com/naver/maps/map/app/OpenSourceLicenseActivity.html";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        return root;
    }
}