package com.recursia.popularmovies.data.mappers;

import com.recursia.popularmovies.data.models.GenreDatabaseModel;
import com.recursia.popularmovies.domain.models.Genre;
import com.recursia.popularmovies.utils.Mapper;

public class GenreDatabaseModelToEntityMapper extends Mapper<GenreDatabaseModel, Genre> {
    @Override
    public Genre transform(GenreDatabaseModel genreDatabaseModel) {
        Genre genre = new Genre();
        genre.setId(genreDatabaseModel.getId());
        genre.setMovieId(genreDatabaseModel.getMovieId());
        genre.setName(genreDatabaseModel.getName());
        return genre;
    }
}
