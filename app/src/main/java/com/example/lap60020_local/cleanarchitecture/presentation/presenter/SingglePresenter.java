package com.example.lap60020_local.cleanarchitecture.presentation.presenter;

public interface SingglePresenter {

    void load(int id);
    void setView(ISinglelView view);
    void detachView();
}
