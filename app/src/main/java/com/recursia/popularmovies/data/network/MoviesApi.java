package com.recursia.popularmovies.data.network;

import com.recursia.popularmovies.data.models.CreditsResponse;
import com.recursia.popularmovies.data.models.DiscoverMoviesResponse;
import com.recursia.popularmovies.data.models.MovieDatabaseModel;
import com.recursia.popularmovies.data.models.MovieTrailersResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApi {
    String API_KEY = "b158b97e42698b2da042bf942864f95f";

    @GET("3/discover/movie?api_key=" + API_KEY)
    Single<DiscoverMoviesResponse> discoverMovies(@Query("sort_by") String sortBy, @Query("page") int page, @Query("vote_count.gte") int voteCount, @Query("language") String language);

    @GET("3/movie/{id}/videos?api_key=" + API_KEY)
    Single<MovieTrailersResponse> getMovieTrailersById(@Path("id") int id, @Query("language") String language);

    @GET("3/movie/{id}?api_key=" + API_KEY)
    Single<MovieDatabaseModel> getMovieById(@Path("id") int id, @Query("language") String language);

    @GET("3/movie/{id}/credits?api_key=" + API_KEY)
    Single<CreditsResponse> getMovieCreditsById(@Path("id") int id);

    @GET("3/movie/{id}/recommendations?api_key=" + API_KEY)
    Single<DiscoverMoviesResponse> getMovieRecommendations(@Path("id") int id, @Query("page") int page, @Query("language") String language);

    @GET("3/search/movie?api_key=" + API_KEY)
    Single<DiscoverMoviesResponse> getMoviesByQuery(@Query("query") String query, @Query("page") int page, @Query("language") String language);

}