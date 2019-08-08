package com.example.popularmovies.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesService {
    private static final String BASE_URl = "https://api.themoviedb.org/";
    private static MoviesService instance;
    private Retrofit retrofit;

    private MoviesService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static synchronized MoviesService getInstance() {
        if (instance == null) {
            instance = new MoviesService();
        }
        return instance;
    }

    public MoviesApi getMoviesApi() {
        return retrofit.create(MoviesApi.class);
    }

}
