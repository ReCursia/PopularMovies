package com.example.popularmovies.repository;

import com.example.popularmovies.pojo.DiscoverMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApi {
    String API_KEY = "b158b97e42698b2da042bf942864f95f";
    String LANGUAGE_VALUE = "ru-RU";

    @GET("3/discover/movie?api_key=" + API_KEY + "&language=" + LANGUAGE_VALUE)
    Call<DiscoverMovies> discoverMovies(@Query("sort_by") String sortBy, @Query("page") int page);
}