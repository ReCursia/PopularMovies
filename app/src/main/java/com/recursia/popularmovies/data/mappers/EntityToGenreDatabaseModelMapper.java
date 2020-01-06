package com.recursia.popularmovies.data.mappers;

import com.recursia.popularmovies.data.models.GenreDatabaseModel;
import com.recursia.popularmovies.domain.models.Genre;
import com.recursia.popularmovies.utils.Mapper;

public class EntityToGenreDatabaseModelMapper extends Mapper<Genre, GenreDatabaseModel> {
    @Override
    public GenreDatabaseModel transform(Genre genre) {
        GenreDatabaseModel genreDatabaseModel = new GenreDatabaseModel();
        genreDatabaseModel.setId(genre.getId());
        genreDatabaseModel.setMovieId(genre.getMovieId());
        genreDatabaseModel.setName(genre.getName());
        return genreDatabaseModel;
    }
}
