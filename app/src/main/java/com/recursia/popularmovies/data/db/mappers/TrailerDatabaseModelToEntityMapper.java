package com.recursia.popularmovies.data.db.mappers;

import com.recursia.popularmovies.data.models.TrailerDatabaseModel;
import com.recursia.popularmovies.domain.models.Trailer;
import com.recursia.popularmovies.utils.Mapper;

public class TrailerDatabaseModelToEntityMapper extends Mapper<TrailerDatabaseModel, Trailer> {
    @Override
    public Trailer transform(TrailerDatabaseModel trailerDatabaseModel) {
        Trailer trailer = new Trailer();
        trailer.setId(trailerDatabaseModel.getId());
        trailer.setKey(trailerDatabaseModel.getKey());
        trailer.setMovieId(trailerDatabaseModel.getMovieId());
        trailer.setName(trailerDatabaseModel.getName());
        trailer.setType(trailerDatabaseModel.getType());
        trailer.setSite(trailerDatabaseModel.getSite());
        trailer.setSize(trailerDatabaseModel.getSize());
        return trailer;
    }
}
