package com.example.lap60020_local.cleanarchitecture;

import android.app.Application;

import com.example.lap60020_local.cleanarchitecture.data.DetailRepository;
import com.example.lap60020_local.cleanarchitecture.data.MovieApi;
import com.example.lap60020_local.cleanarchitecture.data.MyApiClient;
import com.example.lap60020_local.cleanarchitecture.data.PopularRepository;
import com.example.lap60020_local.cleanarchitecture.data.SearchRepositoy;
import com.example.lap60020_local.cleanarchitecture.data.TopRatedRepository;
import com.example.lap60020_local.cleanarchitecture.domain.PopularUsecase;
import com.example.lap60020_local.cleanarchitecture.domain.SearchUsecase;
import com.example.lap60020_local.cleanarchitecture.domain.TopratedUsecase;
import com.example.lap60020_local.cleanarchitecture.presentation.presenter.DetailPresenter;
import com.example.lap60020_local.cleanarchitecture.presentation.presenter.PopularPresenter;
import com.example.lap60020_local.cleanarchitecture.presentation.presenter.SearchPresenter;
import com.example.lap60020_local.cleanarchitecture.presentation.presenter.TopratedPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyApplication extends Application {

    private PopularPresenter popularPresenter;
    private TopratedPresenter topratedPresenter;
    private SearchPresenter searchPresenter;
    private DetailPresenter detailPresenter;
    private PopularRepository popularRepository;
    private TopRatedRepository topRatedRepository;
    private SearchRepositoy searchRepositoy;
    private DetailRepository detailRepository;
    private MovieApi api;
    @Override
    public void onCreate() {
        super.onCreate();
        api = MyApiClient.getInstance().create(MovieApi.class);
        popularRepository = new PopularRepository(api);
        topRatedRepository = new TopRatedRepository(api);
        searchRepositoy = new SearchRepositoy(api);
        detailRepository = new DetailRepository(api);

        popularPresenter = new PopularPresenter(new PopularUsecase(popularRepository,
                Schedulers.io(),
                AndroidSchedulers.mainThread()));
        topratedPresenter = new TopratedPresenter(new TopratedUsecase(topRatedRepository,
                Schedulers.io(),
                AndroidSchedulers.mainThread()));

        searchPresenter = new SearchPresenter(new SearchUsecase(searchRepositoy,
                Schedulers.io(),
                AndroidSchedulers.mainThread()));

        detailPresenter = new DetailPresenter();
    }

    public PopularPresenter getPopularPresenter() {
        return popularPresenter;
    }

    public TopratedPresenter getTopratedPresenter() {
        return topratedPresenter;
    }

    public SearchPresenter getSearchPresenter() {
        return searchPresenter;
    }

    public DetailPresenter getDetailPresenter() {
        return detailPresenter;
    }

    public PopularRepository getPopularRepository() {
        return popularRepository;
    }

    public TopRatedRepository getTopRatedRepository() {
        return topRatedRepository;
    }

    public SearchRepositoy getSearchRepositoy() {
        return searchRepositoy;
    }

    public DetailRepository getDetailRepository() {
        return detailRepository;
    }
}
