package com.sayurklik.app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.sayurklik.app.adapters.feeds.FeedHeaderAdapter;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.items.FeedItem;
import com.synnapps.carouselview.CarouselView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    View view;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        /*carouselView = view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);
        // Inflate the layout for this fragment
        ShimmerFrameLayout shimmerFrameLayout = view.findViewById(R.id.shimmerFrameLayout);
        shimmerFrameLayout.startShimmer();*/

        loadSlider();
        loadFeed();
        return view;
    }
   private void loadSlider(){
       getActivity()
               .getSupportFragmentManager()
               .beginTransaction()
               .replace(R.id.fl_slider, new SliderFragment())
               .commit();
   }
    private void loadFeed(){
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_feed, new FeedFragment())
                .commit();
    }
}
