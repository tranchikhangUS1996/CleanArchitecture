package com.example.lap60020_local.cleanarchitecture.presentation.presenter;

import com.example.lap60020_local.cleanarchitecture.data.DetailRepository;
import com.example.lap60020_local.cleanarchitecture.data.Movie;
import com.example.lap60020_local.cleanarchitecture.domain.DetailUsecase;

import io.reactivex.observers.DisposableObserver;

public class DetailPresenter implements SingglePresenter {

    private ISinglelView view;
    private DetailUsecase detailUsecase;

    @Override
    public void load(int id) {
        view.showLoading();
        detailUsecase.execute(new DetailObserver(), DetailRepository.DetailParam.setId(id));
    }

    @Override
    public void setView(ISinglelView iView) {
        this.view = iView;
    }

    @Override
    public void detachView() {
        view = null;
    }

    public class DetailObserver extends DisposableObserver<Movie> {

        @Override
        public void onNext(Movie users) {
            view.receiveData(users);
        }

        @Override
        public void onError(Throwable e) {
            view.showError(e.getMessage());
        }

        @Override
        public void onComplete() {
            view.removeLoading();
            view.show();
        }
    }

}
