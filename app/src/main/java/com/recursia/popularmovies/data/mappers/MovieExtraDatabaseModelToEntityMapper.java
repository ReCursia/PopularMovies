package com.recursia.popularmovies.data.mappers;

import com.recursia.popularmovies.data.models.MovieDatabaseModel;
import com.recursia.popularmovies.data.models.MovieExtraDatabaseModel;
import com.recursia.popularmovies.domain.models.Movie;
import com.recursia.popularmovies.utils.Mapper;

public class MovieExtraDatabaseModelToEntityMapper extends Mapper<MovieExtraDatabaseModel, Movie> {
    private final GenreDatabaseModelToEntityMapper genreDatabaseModelToEntityMapper;
    private final CastDatabaseModelToEntityMapper castDatabaseModelToEntityMapper;
    private final TrailerDatabaseModelToEntityMapper trailerDatabaseModelToEntityMapper;

    public MovieExtraDatabaseModelToEntityMapper(GenreDatabaseModelToEntityMapper genreDatabaseModelToEntityMapper,
                                                 CastDatabaseModelToEntityMapper castDatabaseModelToEntityMapper,
                                                 TrailerDatabaseModelToEntityMapper trailerDatabaseModelToEntityMapper) {
        this.genreDatabaseModelToEntityMapper = genreDatabaseModelToEntityMapper;
        this.castDatabaseModelToEntityMapper = castDatabaseModelToEntityMapper;
        this.trailerDatabaseModelToEntityMapper = trailerDatabaseModelToEntityMapper;
    }

    @Override
    public Movie transform(MovieExtraDatabaseModel movieExtraDatabaseModel) {
        Movie movie = new Movie();
        movie.setGenres(genreDatabaseModelToEntityMapper.transform(movieExtraDatabaseModel.getGenres()));
        movie.setTrailers(trailerDatabaseModelToEntityMapper.transform(movieExtraDatabaseModel.getTrailers()));
        movie.setCasts(castDatabaseModelToEntityMapper.transform(movieExtraDatabaseModel.getCast()));
        //Movie
        MovieDatabaseModel movieDatabaseModel = movieExtraDatabaseModel.getMovie();
        movie.setFavorite(movieDatabaseModel.isFavorite());
        movie.setVoteCount(movieDatabaseModel.getVoteCount());
        movie.setVoteAverage(movieDatabaseModel.getVoteAverage());
        movie.setVideo(movieDatabaseModel.isVideo());
        movie.setTitle(movieDatabaseModel.getTitle());
        movie.setBudget(movieDatabaseModel.getBudget());
        movie.setBackdropPath(movieDatabaseModel.getBackdropPath());
        movie.setId(movieDatabaseModel.getId());
        movie.setOriginalTitle(movieDatabaseModel.getOriginalTitle());
        movie.setPopularity(movieDatabaseModel.getPopularity());
        movie.setOriginalLanguage(movieDatabaseModel.getOriginalLanguage());
        movie.setOverview(movieDatabaseModel.getOverview());
        movie.setPosterPath(movieDatabaseModel.getPosterPath());
        movie.setReleaseDate(movieDatabaseModel.getReleaseDate());
        movie.setStatus(movieDatabaseModel.getStatus());
        movie.setTagline(movieDatabaseModel.getTagline());
        movie.setRuntime(movieDatabaseModel.getRuntime());
        movie.setOriginalTitle(movieDatabaseModel.getOriginalTitle());
        return movie;
    }
}
