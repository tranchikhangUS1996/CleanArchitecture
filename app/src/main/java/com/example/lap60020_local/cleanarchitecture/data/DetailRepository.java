package com.example.lap60020_local.cleanarchitecture.data;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;

public class DetailRepository implements Repository<Movie, DetailRepository.DetailParam> {

    private MovieApi api;

    public DetailRepository(MovieApi api) {
        this.api = api;
    }

    @Override
    public Observable<Movie> getData(DetailParam detailParam) {
        return Observable.just(detailParam.Id).map(id->{
           Call<Movie> call =  api.getMovieDetails(id,MyApiClient.MY_KEY);
            Response<Movie> response = call.execute();
            return response.body();
        });
    }

    @Override
    public Observable<Movie> getMoreData(DetailParam detailParam) {
        return null;
    }

    public static final class DetailParam{
        private int Id;
        private DetailParam(int id) {
            Id = id;
        }
        public static DetailParam setId(int id) {
            return new DetailParam(id);
        }
    }
}
