package com.example.popularmovies.repository;

import com.example.popularmovies.pojo.DiscoverMovies;
import com.example.popularmovies.pojo.Movie;
import com.example.popularmovies.pojo.MovieTrailers;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApi {
    String API_KEY = "b158b97e42698b2da042bf942864f95f";
    String LANGUAGE_VALUE = "ru-RU";

    @GET("3/discover/movie?api_key=" + API_KEY + "&language=" + LANGUAGE_VALUE)
    Observable<DiscoverMovies> discoverMovies(@Query("sort_by") String sortBy, @Query("page") int page);

    @GET("/3/movie/{id}/videos?api_key=" + API_KEY)
    Observable<MovieTrailers> getMovieTrailersById(@Path("id") int id);

    @GET("3/movie/{id}?api_key=" + API_KEY + "&language=" + LANGUAGE_VALUE)
    Observable<Movie> getMovieById(@Path("id") int id);

}