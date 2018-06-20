package com.example.lap60020_local.cleanarchitecture.domain;

import com.example.lap60020_local.cleanarchitecture.data.PopularRepository;
import com.example.lap60020_local.cleanarchitecture.data.Repository;
import com.example.lap60020_local.cleanarchitecture.data.Movie;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class PopularUsecase extends ListUsecase<List<Movie>, PopularRepository.PopularParams> {

    Repository<List<Movie>,PopularRepository.PopularParams> repository;

    public PopularUsecase(Repository<List<Movie>,PopularRepository.PopularParams> repository,
                          Scheduler subscribeOnThread,
                          Scheduler observeOnThread) {

        super(subscribeOnThread, observeOnThread);
        this.repository = repository;
    }

    @Override
    public Observable<List<Movie>> buildUsecase(PopularRepository.PopularParams params) {
        return repository.getData(params);
    }

    @Override
    public Observable<List<Movie>> buildLoadMoreObservable(PopularRepository.PopularParams params) {
        return repository.getMoreData(params);
    }
}
