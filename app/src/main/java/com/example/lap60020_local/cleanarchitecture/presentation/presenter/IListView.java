package com.example.lap60020_local.cleanarchitecture.presentation.presenter;

import com.example.lap60020_local.cleanarchitecture.data.Movie;

import java.util.List;

public interface IListView extends IView {

    void receiveData(List<Movie> users);

    void receiveMoreData(List<Movie> users);

    void show(int pos);

    void updateList(int pos);
}
