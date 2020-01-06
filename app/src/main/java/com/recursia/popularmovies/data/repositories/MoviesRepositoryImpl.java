package com.recursia.popularmovies.data.repositories;

import com.recursia.popularmovies.data.db.MovieDao;
import com.recursia.popularmovies.data.db.mappers.EntityToMovieExtraDatabaseModelMapper;
import com.recursia.popularmovies.data.db.mappers.MovieDatabaseModelToEntityMapper;
import com.recursia.popularmovies.data.db.mappers.MovieExtraDatabaseModelToEntityMapper;
import com.recursia.popularmovies.data.network.MoviesApi;
import com.recursia.popularmovies.data.network.mappers.CreditsResponseToCastMapper;
import com.recursia.popularmovies.data.network.mappers.DiscoverMovieResponseToMovieMapper;
import com.recursia.popularmovies.data.network.mappers.MovieTrailersResponseToTrailersMapper;
import com.recursia.popularmovies.domain.MoviesRepository;
import com.recursia.popularmovies.domain.models.Cast;
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.domain.models.Trailer;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class MoviesRepositoryImpl implements MoviesRepository {

    private final MovieDao movieDao;
    private final MoviesApi moviesApi;
    private final MovieDatabaseModelToEntityMapper movieDatabaseModelToEntityMapper;
    private final DiscoverMovieResponseToMovieMapper discoverMovieResponseToMovieMapper;
    private final CreditsResponseToCastMapper creditsResponseToCastMapper;
    private final MovieTrailersResponseToTrailersMapper movieTrailersResponseToTrailersMapper;
    private final MovieExtraDatabaseModelToEntityMapper movieExtraDatabaseModelToEntityMapper;
    private final EntityToMovieExtraDatabaseModelMapper entityToMovieExtraDatabaseModelMapper;

    public MoviesRepositoryImpl(MovieDao movieDao,
                                MoviesApi moviesApi,
                                MovieDatabaseModelToEntityMapper movieDatabaseModelToEntityMapper,
                                DiscoverMovieResponseToMovieMapper discoverMovieResponseToMovieMapper,
                                CreditsResponseToCastMapper creditsResponseToCastMapper,
                                MovieTrailersResponseToTrailersMapper movieTrailersResponseToTrailersMapper,
                                MovieExtraDatabaseModelToEntityMapper movieExtraDatabaseModelToEntityMapper,
                                EntityToMovieExtraDatabaseModelMapper entityToMovieExtraDatabaseModelMapper) {
        this.movieDao = movieDao;
        this.moviesApi = moviesApi;
        this.movieDatabaseModelToEntityMapper = movieDatabaseModelToEntityMapper;
        this.discoverMovieResponseToMovieMapper = discoverMovieResponseToMovieMapper;
        this.creditsResponseToCastMapper = creditsResponseToCastMapper;
        this.movieTrailersResponseToTrailersMapper = movieTrailersResponseToTrailersMapper;
        this.movieExtraDatabaseModelToEntityMapper = movieExtraDatabaseModelToEntityMapper;
        this.entityToMovieExtraDatabaseModelMapper = entityToMovieExtraDatabaseModelMapper;
    }

    @Override
    public Single<List<Movie>> discoverMovies(String sortBy, int page, int voteCount, String language) {
        return moviesApi.discoverMovies(sortBy, page, voteCount, language)
                .subscribeOn(Schedulers.io())
                .map(discoverMovieResponseToMovieMapper::transform);

    }

    @Override
    public Flowable<List<Movie>> getAllFavoriteMovies() {
        return movieDao.getAllMovies()
                .subscribeOn(Schedulers.io())
                .map(movieExtraDatabaseModelToEntityMapper::transform);
    }

    @Override
    public Single<Movie> getMovieById(int movieId, String language) {
        return movieDao.getMovieById(movieId)
                .map(movieExtraDatabaseModelToEntityMapper::transform)
                .onErrorResumeNext((t) -> loadMovieFromNetwork(movieId, language))
                .subscribeOn(Schedulers.io());
    }

    private Single<Movie> loadMovieFromNetwork(int movieId, String language) {
        Single<Movie> movieSingle = moviesApi.getMovieById(movieId, language)
                .map(movieDatabaseModelToEntityMapper::transform);
        Single<List<Cast>> castListSingle = moviesApi.getMovieCreditsById(movieId)
                .map(creditsResponseToCastMapper::transform);
        Single<List<Trailer>> trailerListSingle = moviesApi.getMovieTrailersById(movieId, language)
                .map(movieTrailersResponseToTrailersMapper::transform);
        return Single.zip(movieSingle, castListSingle, trailerListSingle, (m, c, t) -> {
            m.setCasts(c);
            m.setTrailers(t);
            return m;
        });
    }

    @Override
    public Single<List<Movie>> getMoviesByQuery(String query, int page, String language) {
        return moviesApi.getMoviesByQuery(query, page, language)
                .subscribeOn(Schedulers.io())
                .map(discoverMovieResponseToMovieMapper::transform);
    }

    @Override
    public Single<List<Movie>> getMovieRecommendations(int movieId, int page, String language) {
        return moviesApi.getMovieRecommendations(movieId, page, language)
                .subscribeOn(Schedulers.io())
                .map(discoverMovieResponseToMovieMapper::transform);
    }

    @Override
    public Completable makeFavoriteMovie(Movie movie) {
        return Completable.fromAction(() -> movieDao.insertMovieExtra(entityToMovieExtraDatabaseModelMapper.transform(movie)))
                .doOnSubscribe(disposable -> movie.setFavorite(true))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable removeFavoriteMovie(Movie movie) {
        return Completable.fromAction(() -> movieDao.deleteMovieExtra(entityToMovieExtraDatabaseModelMapper.transform(movie)))
                .doOnSubscribe(disposable -> movie.setFavorite(false))
                .subscribeOn(Schedulers.io());
    }
}
