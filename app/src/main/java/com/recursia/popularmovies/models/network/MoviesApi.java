package com.recursia.popularmovies.models.network;

import com.recursia.popularmovies.models.pojo.Credits;
import com.recursia.popularmovies.models.pojo.DiscoverMovies;
import com.recursia.popularmovies.models.pojo.Movie;
import com.recursia.popularmovies.models.pojo.MovieTrailers;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApi {
    String API_KEY = "b158b97e42698b2da042bf942864f95f";

    @GET("3/discover/movie?api_key=" + API_KEY)
    Single<DiscoverMovies> discoverMovies(@Query("sort_by") String sortBy, @Query("page") int page, @Query("vote_count.gte") int voteCount, @Query("language") String language);

    @GET("3/movie/{id}/videos?api_key=" + API_KEY)
    Single<MovieTrailers> getMovieTrailersById(@Path("id") int id, @Query("language") String language);

    @GET("3/movie/{id}?api_key=" + API_KEY)
    Single<Movie> getMovieById(@Path("id") int id, @Query("language") String language);

    @GET("3/movie/{id}/credits?api_key=" + API_KEY)
    Single<Credits> getMovieCreditsById(@Path("id") int id);

    @GET("3/movie/{id}/recommendations?api_key=" + API_KEY)
    Single<DiscoverMovies> getMovieRecommendations(@Path("id") int id, @Query("page") int page, @Query("language") String language);

}