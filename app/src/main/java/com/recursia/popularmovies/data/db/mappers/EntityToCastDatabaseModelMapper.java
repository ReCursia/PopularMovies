package com.recursia.popularmovies.data.db.mappers;

import com.recursia.popularmovies.data.models.CastDatabaseModel;
import com.recursia.popularmovies.domain.models.Cast;
import com.recursia.popularmovies.utils.Mapper;

public class EntityToCastDatabaseModelMapper extends Mapper<Cast, CastDatabaseModel> {
    @Override
    public CastDatabaseModel transform(Cast cast) {
        CastDatabaseModel castDatabaseModel = new CastDatabaseModel();
        castDatabaseModel.setCastId(cast.getCastId());
        castDatabaseModel.setCharacter(cast.getCharacter());
        castDatabaseModel.setCreditId(cast.getCreditId());
        castDatabaseModel.setGender(cast.getGender());
        castDatabaseModel.setId(cast.getId());
        castDatabaseModel.setName(cast.getName());
        castDatabaseModel.setProfilePath(cast.getProfilePath());
        castDatabaseModel.setMovieId(cast.getMovieId());
        castDatabaseModel.setOrder(cast.getOrder());
        return castDatabaseModel;
    }
}
