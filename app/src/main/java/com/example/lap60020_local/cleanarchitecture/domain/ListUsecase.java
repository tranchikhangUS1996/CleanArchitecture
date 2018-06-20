package com.example.lap60020_local.cleanarchitecture.domain;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public abstract class ListUsecase<T, Params> extends UseCase<T,Params> {


    private final Scheduler subscribeOnThread;
    private final Scheduler observeOnThread;
    private CompositeDisposable disposables;

    ListUsecase(Scheduler subscribeOnThread, Scheduler observeOnThread) {
        super(subscribeOnThread, observeOnThread);
        this.subscribeOnThread = subscribeOnThread;
        this.observeOnThread = observeOnThread;
        disposables = new CompositeDisposable();
    }

    public abstract Observable<T> buildLoadMoreObservable(Params params);

    public void executeLoadMore(DisposableObserver<T> observer, Params params) {
        Observable<T> observable = buildLoadMoreObservable(params)
                .subscribeOn(subscribeOnThread)
                .observeOn(observeOnThread);
        disposables.add(observable.subscribeWith(observer));
    }

    public void dispose() {
        super.disConnect();
        disposables.dispose();
    }
}
