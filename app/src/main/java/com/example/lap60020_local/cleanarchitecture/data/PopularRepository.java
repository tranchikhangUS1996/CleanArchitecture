package com.example.lap60020_local.cleanarchitecture.data;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;

public class PopularRepository implements Repository<List<Movie>, PopularRepository.PopularParams> {

    private MovieApi api;
    private int TotalPage;
    private List<Movie> movies;

    public PopularRepository(MovieApi api) {
        this.api = api;
        movies = new ArrayList<>();
    }

    public int getTotalPage() {
        return TotalPage;
    }

    @Override
    public Observable<List<Movie>> getData(PopularParams params) {
        if(movies.size() != 0 ) {
            List<Movie> clone = new ArrayList<>();
            clone.addAll(movies);
            return Observable.just(clone);
        } else {
            return Observable.just(params.Page).map(page -> {
                Call<MovieResponde> call = api.getPopularMovies(page, MyApiClient.MY_KEY);
                Response<MovieResponde> response = call.execute();
                TotalPage = response.body().getTotalPages();
                movies = response.body().getResults();
                return new ArrayList<>(movies);
            });
        }
    }

    @Override
    public Observable<List<Movie>> getMoreData(PopularParams params) {
        return Observable.just(params.Page).map(page->{
            Call<MovieResponde> call = api.getPopularMovies(page, MyApiClient.MY_KEY);
            Response<MovieResponde> response = call.execute();
            TotalPage = response.body().getTotalPages();
            List<Movie> list = response.body().getResults();
            movies.addAll(list);
            return list;
        });
    }


    public static final class PopularParams {
        private int Page;

        private PopularParams(int page) {
            Page = page;
        }
        public static PopularParams setPage(int page) {
            return new PopularParams(page);
        }
    }

}
