package com.recursia.popularmovies.data.db.mappers;

import com.recursia.popularmovies.data.models.MovieDatabaseModel;
import com.recursia.popularmovies.data.models.MovieExtraDatabaseModel;
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.utils.Mapper;

public class EntityToMovieExtraDatabaseModelMapper extends Mapper<Movie, MovieExtraDatabaseModel> {
    private final EntityToCastDatabaseModelMapper entityToCastDatabaseModelMapper;
    private final EntityToGenreDatabaseModelMapper entityToGenreDatabaseModelMapper;
    private final EntityToTrailerDatabaseModelMapper entityToTrailerDatabaseModelMapper;

    public EntityToMovieExtraDatabaseModelMapper(EntityToCastDatabaseModelMapper entityToCastDatabaseModelMapper,
                                                 EntityToGenreDatabaseModelMapper entityToGenreDatabaseModelMapper,
                                                 EntityToTrailerDatabaseModelMapper entityToTrailerDatabaseModelMapper) {
        this.entityToCastDatabaseModelMapper = entityToCastDatabaseModelMapper;
        this.entityToGenreDatabaseModelMapper = entityToGenreDatabaseModelMapper;
        this.entityToTrailerDatabaseModelMapper = entityToTrailerDatabaseModelMapper;
    }

    @Override
    public MovieExtraDatabaseModel transform(Movie movie) {
        MovieExtraDatabaseModel movieExtraDatabaseModel = new MovieExtraDatabaseModel();
        movieExtraDatabaseModel.setCast(entityToCastDatabaseModelMapper.transform(movie.getCasts()));
        movieExtraDatabaseModel.setGenres(entityToGenreDatabaseModelMapper.transform(movie.getGenres()));
        movieExtraDatabaseModel.setTrailers(entityToTrailerDatabaseModelMapper.transform(movie.getTrailers()));
        //Movie
        MovieDatabaseModel movieDatabaseModel = new MovieDatabaseModel();
        movieDatabaseModel.setFavorite(movie.isFavorite());
        movieDatabaseModel.setVoteCount(movie.getVoteCount());
        movieDatabaseModel.setVoteAverage(movie.getVoteAverage());
        movieDatabaseModel.setVideo(movie.isVideo());
        movieDatabaseModel.setTitle(movie.getTitle());
        movieDatabaseModel.setBudget(movie.getBudget());
        movieDatabaseModel.setBackdropPath(movie.getBackdropPath());
        movieDatabaseModel.setId(movie.getId());
        movieDatabaseModel.setOriginalTitle(movie.getOriginalTitle());
        movieDatabaseModel.setPopularity(movie.getPopularity());
        movieDatabaseModel.setOriginalLanguage(movie.getOriginalLanguage());
        movieDatabaseModel.setOverview(movie.getOverview());
        movieDatabaseModel.setPosterPath(movie.getPosterPath());
        movieDatabaseModel.setReleaseDate(movie.getReleaseDate());
        movieDatabaseModel.setStatus(movie.getStatus());
        movieDatabaseModel.setTagline(movie.getTagline());
        movieDatabaseModel.setRuntime(movie.getRuntime());
        movieDatabaseModel.setOriginalTitle(movie.getOriginalTitle());
        movieExtraDatabaseModel.setMovie(movieDatabaseModel);
        return movieExtraDatabaseModel;
    }
}
