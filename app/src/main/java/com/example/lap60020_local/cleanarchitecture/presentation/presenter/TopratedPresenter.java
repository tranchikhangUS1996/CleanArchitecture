package com.example.lap60020_local.cleanarchitecture.presentation.presenter;

import com.example.lap60020_local.cleanarchitecture.data.Movie;
import com.example.lap60020_local.cleanarchitecture.data.TopRatedRepository;
import com.example.lap60020_local.cleanarchitecture.domain.TopratedUsecase;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class TopratedPresenter implements ListPresenter{

    private IListView view;
    private TopratedUsecase topratedUsecase;
    int page = 1;
    private int current = 1;
    private int total = 0;
    private boolean isLoading = false;

    public TopratedPresenter(TopratedUsecase topratedUsecase) {
        this.topratedUsecase = topratedUsecase;
    }

    @Override
    public void loadData() {
        if(isLoading) return;
        isLoading = true;
        view.showLoading();
        topratedUsecase.execute(new TopratedObserver(), TopRatedRepository.TopratedParam.setPage(page));
        page++;
    }

    @Override
    public void loadMoreData(int lastPosition) {
        current = lastPosition;
        if(lastPosition +1 == total) {
            if(isLoading) return;
            isLoading = true;
            view.showLoading();
            topratedUsecase.executeLoadMore(new TopratedLoadMoreObserver(), TopRatedRepository.TopratedParam.setPage(page));
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

    public class TopratedLoadMoreObserver extends DisposableObserver<List<Movie>> {

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

    public class TopratedObserver extends DisposableObserver<List<Movie>> {

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
