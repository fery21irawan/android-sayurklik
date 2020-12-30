package com.sayurklik.app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sayurklik.app.helpers.ProgressBarHelper;
import com.sayurklik.app.models.TagihanModel;

import org.json.JSONArray;
import org.json.JSONException;

public class TagihanFragment extends Fragment {
    View view;
    Bundle bundle;
    RecyclerView recyclerView;
    ProgressBarHelper mPbh;
    public TagihanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tagihan, container, false);
        bundle = getArguments();
        recyclerView = view.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mPbh = view.findViewById(R.id.pbh);
        getTagihan();
        return view;
    }
    private void getTagihan(){
        new TagihanModel.Get(getContext())
                .setQueryParameter(consts.STATUS, bundle.getString(consts.STATUS))
                .setProgressBar(mPbh)
                .showInto(recyclerView);
        /*try {
            JSONArray array = new JSONArray(bundle.getString(consts.DATA));
            new TagihanModel.Get(getContext())
                    .setData(array)
                    .showInto(recyclerView);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }
}
