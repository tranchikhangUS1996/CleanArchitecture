package com.example.lap60020_local.cleanarchitecture.domain;

import com.example.lap60020_local.cleanarchitecture.data.Repository;
import com.example.lap60020_local.cleanarchitecture.data.TopRatedRepository;
import com.example.lap60020_local.cleanarchitecture.data.Movie;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class TopratedUsecase extends ListUsecase<List<Movie>,TopRatedRepository.TopratedParam> {

    private Repository<List<Movie>, TopRatedRepository.TopratedParam> repository;

    public TopratedUsecase(Repository<List<Movie>, TopRatedRepository.TopratedParam> repository,
                    Scheduler subscribeOnThread,
                    Scheduler observeOnThread) {

        super(subscribeOnThread, observeOnThread);
        this.repository = repository;
    }

    @Override
    public Observable<List<Movie>> buildUsecase(TopRatedRepository.TopratedParam topratedParam) {
        return repository.getData(topratedParam);
    }

    @Override
    public Observable<List<Movie>> buildLoadMoreObservable(TopRatedRepository.TopratedParam topratedParam) {
        return repository.getMoreData(topratedParam);
    }

}
