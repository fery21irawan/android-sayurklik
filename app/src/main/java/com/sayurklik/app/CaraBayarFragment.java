package com.sayurklik.app;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CaraBayarFragment extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cara_bayar, container);
        if(getArguments() != null){
            String bayar = getArguments().getString("notrx");
            Toast.makeText(getContext(), "id trx " + bayar, Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}
