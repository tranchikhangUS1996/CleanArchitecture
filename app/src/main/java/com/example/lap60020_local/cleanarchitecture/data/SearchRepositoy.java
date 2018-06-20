package com.example.lap60020_local.cleanarchitecture.data;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;

public class SearchRepositoy implements Repository<List<Movie>, SearchRepositoy.SearchParam> {

    private MovieApi api;
    private int TotalPage = 1;
    private List<Movie> movies;

    public SearchRepositoy(MovieApi api) {
        this.api = api;
        movies = new ArrayList<>();
    }

    public int getTotalPage() {
        return TotalPage;
    }

    @Override
    public Observable<List<Movie>> getData(SearchParam searchParam) {
        if(movies.size() != 0 ) {
            List<Movie> clone = new ArrayList<>();
            clone.addAll(movies);
            return Observable.just(clone);
        } else {
            return Observable.just(searchParam).map(param -> {
                Call<MovieResponde> call = api.getSearchMovies(param.Key, param.Page, MyApiClient.MY_KEY);
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
    public Observable<List<Movie>> getMoreData(SearchParam searchParam) {
        return Observable.just(searchParam).map(param->{
            Call<MovieResponde> call = api.getSearchMovies(param.Key, param.Page ,MyApiClient.MY_KEY);
            Response<MovieResponde> response = call.execute();
            TotalPage = response.body().getTotalPages();
            List<Movie> list = response.body().getResults();
            movies.addAll(list);
            return list;
        });
    }


    public static class SearchParam {
        private int Page;
        private String Key;

        private SearchParam(int page, String key) {
            Page = page;
            Key = key;
        }

        public static SearchParam setParam(int page, String key) {
            return new SearchParam(page, key);
        }
    }
}
