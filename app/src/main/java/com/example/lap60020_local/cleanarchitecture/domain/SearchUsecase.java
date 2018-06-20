package com.example.lap60020_local.cleanarchitecture.domain;

import com.example.lap60020_local.cleanarchitecture.data.Repository;
import com.example.lap60020_local.cleanarchitecture.data.SearchRepositoy;
import com.example.lap60020_local.cleanarchitecture.data.Movie;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class SearchUsecase extends ListUsecase<List<Movie>, SearchRepositoy.SearchParam> {
    Repository<List<Movie>, SearchRepositoy.SearchParam> repository;

    public SearchUsecase(Repository<List<Movie>, SearchRepositoy.SearchParam> repository,
                  Scheduler subscribeOnThread,
                  Scheduler observeOnThread) {

        super(subscribeOnThread, observeOnThread);
        this.repository = repository;
    }

    @Override
    public Observable<List<Movie>> buildUsecase(SearchRepositoy.SearchParam searchParam) {
        return repository.getData(searchParam);
    }

    @Override
    public Observable<List<Movie>> buildLoadMoreObservable(SearchRepositoy.SearchParam searchParam) {
        return repository.getMoreData(searchParam);
    }
}
