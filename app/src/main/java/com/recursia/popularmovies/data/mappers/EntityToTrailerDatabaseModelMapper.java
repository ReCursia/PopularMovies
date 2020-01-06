package com.recursia.popularmovies.data.mappers;

import com.recursia.popularmovies.data.models.TrailerDatabaseModel;
import com.recursia.popularmovies.domain.models.Trailer;
import com.recursia.popularmovies.utils.Mapper;

public class EntityToTrailerDatabaseModelMapper extends Mapper<Trailer, TrailerDatabaseModel> {
    @Override
    public TrailerDatabaseModel transform(Trailer trailer) {
        TrailerDatabaseModel trailerDatabaseModel = new TrailerDatabaseModel();
        trailerDatabaseModel.setId(trailer.getId());
        trailerDatabaseModel.setKey(trailer.getKey());
        trailerDatabaseModel.setMovieId(trailer.getMovieId());
        trailerDatabaseModel.setName(trailer.getName());
        trailerDatabaseModel.setSite(trailer.getSite());
        trailerDatabaseModel.setSize(trailer.getSize());
        trailerDatabaseModel.setType(trailer.getType());
        return trailerDatabaseModel;
    }
}
