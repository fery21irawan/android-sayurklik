package com.sayurklik.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.denzcoskun.imageslider.ImageSlider;
import com.sayurklik.app.models.SliderModel;

public class SliderFragment extends Fragment {
    private ImageSlider imageSlider;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        imageSlider = view.findViewById(R.id.image_slider);
        setSlider();
        return view;
    }
    private void setSlider(){
        new SliderModel()
                .setSlider(getContext())
                .into(imageSlider);
    }
}
