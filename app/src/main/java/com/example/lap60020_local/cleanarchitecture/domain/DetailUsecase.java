package com.example.lap60020_local.cleanarchitecture.domain;

import com.example.lap60020_local.cleanarchitecture.data.DetailRepository;
import com.example.lap60020_local.cleanarchitecture.data.Repository;
import com.example.lap60020_local.cleanarchitecture.data.Movie;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class DetailUsecase extends UseCase<Movie, DetailRepository.DetailParam> {

    Repository<Movie,DetailRepository.DetailParam> repository;

    DetailUsecase(Repository<Movie,DetailRepository.DetailParam> repository,
                  Scheduler subscribeOnThread,
                  Scheduler observeOnThread) {

        super(subscribeOnThread, observeOnThread);
        this.repository = repository;
    }

    @Override
    public Observable<Movie> buildUsecase(DetailRepository.DetailParam detailParam) {
        return repository.getData(detailParam);
    }
}
