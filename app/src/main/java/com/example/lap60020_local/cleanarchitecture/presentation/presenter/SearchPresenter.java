package com.example.lap60020_local.cleanarchitecture.presentation.presenter;

import com.example.lap60020_local.cleanarchitecture.data.Movie;
import com.example.lap60020_local.cleanarchitecture.data.SearchRepositoy;
import com.example.lap60020_local.cleanarchitecture.domain.SearchUsecase;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class SearchPresenter implements ListPresenter {

    private IListView view;
    private SearchUsecase searchUsecase;
    private int page = 1;
    private int current = 0;
    private int total = 0;
    private String key;
    private boolean isLoading = false;

    public SearchPresenter(SearchUsecase searchUsecase) {
        this.searchUsecase = searchUsecase;
    }

    public void search(String key){
        this.key = key;
        page = 1;
        loadData();
    }

    @Override
    public void loadData() {
        if(isLoading) return;
        isLoading = true;
        view.showLoading();
        searchUsecase.execute(new SearchObserver(), SearchRepositoy.SearchParam.setParam(page,key));
        page++;
    }

    @Override
    public void loadMoreData(int lastPosition) {
        current = lastPosition;
        if(lastPosition + 1 == total) {
            if(isLoading) return;
            isLoading = true;
            view.showLoading();
            searchUsecase.executeLoadMore(new SearchLoadMoreObserver(), SearchRepositoy.SearchParam.setParam(page,key));
            page++;
        }
    }

    @Override
    public void setView(IListView iView) {
        this.view = iView;
    }

    @Override
    public void detachView() {
        view = null;
        total = 0;
    }

    public class SearchLoadMoreObserver extends DisposableObserver<List<Movie>> {

        @Override
        public void onNext(List<Movie> movies) {
            total += movies.size();
            view.receiveMoreData(movies);
        }

        @Override
        public void onError(Throwable e) {
            isLoading = false;
            view.showError(e.getMessage());
            view.removeLoading();
        }

        @Override
        public void onComplete() {
            isLoading = false;
            view.removeLoading();
            view.updateList(current);
        }
    }

    public class SearchObserver extends DisposableObserver<List<Movie>> {

        @Override
        public void onNext(List<Movie> movies) {
            total += movies.size();
            view.receiveData(movies);
        }

        @Override
        public void onError(Throwable e) {
            isLoading = false;
            view.showError(e.getMessage());
            view.removeLoading();
        }

        @Override
        public void onComplete() {
            isLoading = false;
            view.show(current);
        }
    }

}
