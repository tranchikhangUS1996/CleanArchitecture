package com.example.lap60020_local.cleanarchitecture.presentation.presenter;

public interface ListPresenter {
    void loadData();
    void loadMoreData(int lastPosition);
    void setView(IListView view);
    void detachView();
}
