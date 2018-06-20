package com.example.lap60020_local.cleanarchitecture.presentation.presenter;

import com.example.lap60020_local.cleanarchitecture.data.Movie;

import java.util.List;

public interface IView {

    void showLoading();

    void removeLoading();

    void showError(String message);

}
