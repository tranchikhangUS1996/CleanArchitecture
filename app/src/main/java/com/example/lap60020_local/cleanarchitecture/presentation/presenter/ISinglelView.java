package com.example.lap60020_local.cleanarchitecture.presentation.presenter;

import com.example.lap60020_local.cleanarchitecture.data.Movie;

public interface ISinglelView extends IView {

    void receiveData(Movie users);

    void show();
}
