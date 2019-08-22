package com.example.popularmovies.models.mockups;

import com.example.popularmovies.models.network.MoviesApi;
import com.example.popularmovies.models.pojo.Credits;
import com.example.popularmovies.models.pojo.DiscoverMovies;
import com.example.popularmovies.models.pojo.Movie;
import com.example.popularmovies.models.pojo.MovieTrailers;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class MockedApi implements MoviesApi {

    @Override
    public Single<DiscoverMovies> discoverMovies(String sortBy, int page, int voteCount, String language) {
        DiscoverMovies discoverMovies = new DiscoverMovies();
        List<Movie> movies = new ArrayList<>();
        /*
        movies.add(new Movie(0, null, 429203, "en", null,
                null, 0.0, "/a4BfxRK8dBgbQqbRxPs8kmLd8LG.jpg",
                null, 11, null, null, "New Era, resolution LONG TITLE for example", false, 5.3, 0,null));
         */
        discoverMovies.getMovies().addAll(movies);
        return Single.just(discoverMovies);
    }

    @Override
    public Single<MovieTrailers> getMovieTrailersById(int id, String language) {
        return null;
    }

    @Override
    public Single<Movie> getMovieById(int id, String language) {
        return null;
    }

    @Override
    public Single<Credits> getMovieCreditsById(int id) {
        return null;
    }

    @Override
    public Single<DiscoverMovies> getMovieRecommendations(int id, int page, String language) {
        return null;
    }

}
