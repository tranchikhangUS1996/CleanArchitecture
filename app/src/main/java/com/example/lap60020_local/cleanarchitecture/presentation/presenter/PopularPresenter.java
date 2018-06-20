package com.example.lap60020_local.cleanarchitecture.presentation.presenter;

import com.example.lap60020_local.cleanarchitecture.data.Movie;
import com.example.lap60020_local.cleanarchitecture.data.PopularRepository;
import com.example.lap60020_local.cleanarchitecture.domain.PopularUsecase;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class PopularPresenter implements ListPresenter{

    private IListView view;
    private PopularUsecase getPopularUsecase;
    private int page = 1;
    private int current = 0;
    int total = 0;
    private boolean isloading = false;

    public PopularPresenter(PopularUsecase usecase) {
        getPopularUsecase = usecase;
    }

    @Override
    public void loadData() {
        if(isloading) return;
        isloading = true;
        view.showLoading();
        getPopularUsecase.execute(new PopularObserver(), PopularRepository.PopularParams.setPage(page));
        page++;
    }

    @Override
    public void loadMoreData(int lastPosition) {
        current = lastPosition;
        if(lastPosition + 1 == total) {
            if(isloading) return;
            isloading = true;
            view.showLoading();
            getPopularUsecase.executeLoadMore(new PopularLoadMoreObserver(), PopularRepository.PopularParams.setPage(page));
            page++;
        }
    }

    @Override
    public void setView(IListView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
        total = 0;
    }

    public class PopularLoadMoreObserver extends DisposableObserver<List<Movie>> {

        @Override
        public void onNext(List<Movie> movies) {
            total+=movies.size();
            view.receiveMoreData(movies);
        }

        @Override
        public void onError(Throwable e) {
            isloading = false;
            view.showError(e.getMessage());
            view.removeLoading();
        }

        @Override
        public void onComplete() {
            isloading = false;
            view.removeLoading();
            view.updateList(current+1);
        }
    }

    public class PopularObserver extends DisposableObserver<List<Movie>> {

        @Override
        public void onNext(List<Movie> users) {
            total+= users.size();
            view.receiveData(users);
        }

        @Override
        public void onError(Throwable e) {
            isloading = false;
            view.showError(e.getMessage());
            view.removeLoading();
        }

        @Override
        public void onComplete() {
            isloading = false;
            view.show(current);
        }
    }
}
