package com.example.lap60020_local.cleanarchitecture.domain;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCase<T,Params> {

    private CompositeDisposable Disposables;
    private Scheduler SubscribeOnThread;
    private Scheduler ObserveOnThread;

    UseCase(Scheduler subscribeOnThread, Scheduler observeOnThread) {
        this.SubscribeOnThread = subscribeOnThread;
        this.ObserveOnThread = observeOnThread;
        Disposables = new CompositeDisposable();
    }

    public abstract Observable<T> buildUsecase(Params params);

    public void execute(DisposableObserver<T> observer, Params params) {
        // subscribe observer with Observable
        Observable<T> observable = buildUsecase(params)
                .subscribeOn(SubscribeOnThread)
                .observeOn(ObserveOnThread);
        Disposables.add(observable.subscribeWith(observer));
    }

    public void disConnect() {
        if(!Disposables.isDisposed()) {
            Disposables.dispose();
        }
    }

}
