package com.example.lap60020_local.cleanarchitecture.data;

import io.reactivex.Observable;

public interface Repository<T, Params> {

    Observable<T> getData(Params params);
    Observable<T> getMoreData(Params params);
}
