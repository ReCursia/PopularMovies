package com.recursia.popularmovies.data.db.mappers;

import com.recursia.popularmovies.data.models.MovieDatabaseModel;
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.utils.Mapper;

public class MovieDatabaseModelToEntityMapper extends Mapper<MovieDatabaseModel, Movie> {
    private final GenreDatabaseModelToEntityMapper genreDatabaseModelToEntityMapper;

    public MovieDatabaseModelToEntityMapper(GenreDatabaseModelToEntityMapper genreDatabaseModelToEntityMapper) {
        this.genreDatabaseModelToEntityMapper = genreDatabaseModelToEntityMapper;
    }

    @Override
    public Movie transform(MovieDatabaseModel movieDatabaseModel) {
        Movie movie = new Movie();
        movie.setGenres(genreDatabaseModelToEntityMapper.transform(movieDatabaseModel.getGenres()));
        movie.setBudget(movieDatabaseModel.getBudget());
        movie.setBackdropPath(movieDatabaseModel.getBackdropPath());
        movie.setFavorite(movieDatabaseModel.isFavorite());
        movie.setId(movieDatabaseModel.getId());
        movie.setOriginalTitle(movieDatabaseModel.getOriginalTitle());
        movie.setOverview(movieDatabaseModel.getOverview());
        movie.setPopularity(movieDatabaseModel.getPopularity());
        movie.setOriginalLanguage(movieDatabaseModel.getOriginalLanguage());
        movie.setPosterPath(movieDatabaseModel.getPosterPath());
        movie.setReleaseDate(movieDatabaseModel.getReleaseDate());
        movie.setRuntime(movieDatabaseModel.getRuntime());
        movie.setStatus(movieDatabaseModel.getStatus());
        movie.setTagline(movieDatabaseModel.getTagline());
        movie.setTitle(movieDatabaseModel.getTitle());
        movie.setVideo(movieDatabaseModel.isVideo());
        movie.setVoteAverage(movieDatabaseModel.getVoteAverage());
        movie.setVoteCount(movieDatabaseModel.getVoteCount());
        return movie;
    }
}
