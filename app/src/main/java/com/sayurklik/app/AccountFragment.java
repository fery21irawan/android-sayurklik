package com.sayurklik.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sayurklik.app.helpers.ProgressBarHelper;
import com.sayurklik.app.models.OrderModel;

public class AccountFragment extends Fragment {
    View view;
//    Bundle bundle;
//    RecyclerView recyclerView;
//    ProgressBarHelper mPbh;
    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        //bundle = getArguments();
        //getOrderList();
        return view;
    }
    private void getOrderList(){
//        new OrderModel.Get(getContext())
//                .setProgressBar(mPbh)
//                .showInto(recyclerView);
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
