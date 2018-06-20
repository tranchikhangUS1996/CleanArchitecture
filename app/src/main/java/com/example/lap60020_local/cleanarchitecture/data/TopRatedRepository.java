package com.example.lap60020_local.cleanarchitecture.data;

import com.example.lap60020_local.cleanarchitecture.domain.ListUsecase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;

public class TopRatedRepository implements Repository<List<Movie>, TopRatedRepository.TopratedParam> {

    private MovieApi api;
    private int TotalPage = 1;
    private List<Movie> movies;

    public TopRatedRepository(MovieApi api) {
        this.api = api;
        movies = new ArrayList<>();
    }

    public int getTotalPage() {
        return TotalPage;
    }

    @Override
    public Observable<List<Movie>> getData(TopratedParam topratedParam) {
        if(movies.size() != 0 ) {
            List<Movie> clone = new ArrayList<>();
            clone.addAll(movies);
            return Observable.just(clone);
        } else {
            return Observable.just(topratedParam.Page).map(page -> {
                Call<MovieResponde> call = api.getTopRatedMovies(page, MyApiClient.MY_KEY);
                Response<MovieResponde> response = call.execute();
                TotalPage = response.body().getTotalPages();
                movies = response.body().getResults();
                List<Movie> clone = new ArrayList<>();
                clone.addAll(movies);
                return clone;
            });
        }
    }

    @Override
    public Observable<List<Movie>> getMoreData(TopratedParam topratedParam) {
        return Observable.just(topratedParam.Page).map(page->{
            Call<MovieResponde> call = api.getTopRatedMovies(page, MyApiClient.MY_KEY);
            Response<MovieResponde> response = call.execute();
            TotalPage = response.body().getTotalPages();
            List<Movie> list = response.body().getResults();
            movies.addAll(list);
            return list;
        });
    }


    public static class TopratedParam{
        private int Page;

        public TopratedParam(int page) {
            Page = page;
        }

        public static TopratedParam setPage(int page) {
            return new TopratedParam(page);
        }
    }
}
