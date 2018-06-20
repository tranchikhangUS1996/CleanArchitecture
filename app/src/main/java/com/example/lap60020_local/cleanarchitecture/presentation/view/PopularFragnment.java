package com.example.lap60020_local.cleanarchitecture.presentation.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.lap60020_local.cleanarchitecture.MyApplication;
import com.example.lap60020_local.cleanarchitecture.R;
import com.example.lap60020_local.cleanarchitecture.presentation.presenter.ListPresenter;

import java.util.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragnment extends Fragment {


    private MyAdapter adapter;

    private ListPresenter presenter;

    public PopularFragnment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_popular_fragnment, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.popular_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(linearLayoutManager);
        presenter = ((MyApplication) getActivity().getApplication()).getPopularPresenter();
        adapter = new MyAdapter(getContext(),recyclerView, presenter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
