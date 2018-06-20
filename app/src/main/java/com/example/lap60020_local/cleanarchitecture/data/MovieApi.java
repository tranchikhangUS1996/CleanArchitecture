package com.example.lap60020_local.cleanarchitecture.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    @GET("movie/top_rated")
    Call<MovieResponde> getTopRatedMovies(@Query("page") int page, @Query("api_key") String apiKey);
    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
    @GET("movie/popular")
    Call<MovieResponde> getPopularMovies(@Query("page") int page, @Query("api_key") String apiKey);
    @GET("search/movie")
    Call<MovieResponde> getSearchMovies(@Query("query") String query, @Query("page") int page,@Query("api_key") String apikey);
}
