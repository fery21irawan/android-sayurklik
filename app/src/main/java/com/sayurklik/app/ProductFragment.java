package com.sayurklik.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sayurklik.app.helpers.ProgressBarHelper;
import com.sayurklik.app.items.CategoryItem;
import com.sayurklik.app.models.ProductModel;

public class ProductFragment extends Fragment {

    public ProductFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private ProgressBarHelper mPbh;
    //private ShimmerFrameLayout mShimmerViewContainer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        mPbh = view.findViewById(R.id.pbh);
        recyclerView = view.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(manager);
        TextView mTextKeyword = view.findViewById(R.id.text_keyword);
        if(mTextKeyword.getVisibility() == View.VISIBLE){
            mTextKeyword.setVisibility(View.GONE);
        }

        if(getArguments() != null){
            mTextKeyword.setVisibility(View.VISIBLE);
            if(getArguments().getParcelable(consts.PARCELABLE) != null){
                CategoryItem categoryItem = getArguments().getParcelable(consts.PARCELABLE);
                mTextKeyword.setText("Kategori : " + categoryItem.getmCategoryName());
                getProductByCategory(categoryItem.getmId());
            }else{
                String keyw = getArguments().getString("keyword");
                mTextKeyword.setText("Cari untuk : "+keyw);
                getProductBySearch(keyw);
            }
        }else{
            getProduct();
        }

        return view;
    }
    private void getProduct(){
        new ProductModel.Get(getContext())
                .setProgressBar(mPbh)
                .showInto(recyclerView);
    }
    private void getProductBySearch(String keyword){
        new ProductModel.Get(getContext())
                .setQueryParameter("search", keyword)
                .setProgressBar(mPbh)
                .showInto(recyclerView);
    }
    private void getProductByCategory(String category){
        new ProductModel.Get(getContext())
                .setQueryParameter("category", category)
                .setProgressBar(mPbh)
                .showInto(recyclerView);
    }
}
