package com.sayurklik.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.sayurklik.app.helpers.ProgressBarHelper;
import com.sayurklik.app.models.FeedModel;
import com.sayurklik.app.models.SliderModel;

public class FeedFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBarHelper mPbh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        mPbh = view.findViewById(R.id.pbh);
        recyclerView = view.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        loadFeed();
        return view;
    }
    private void loadFeed(){
        new FeedModel.Get(getContext())
                .setProgressBar(mPbh)
                .showInto(recyclerView);
    }
}
