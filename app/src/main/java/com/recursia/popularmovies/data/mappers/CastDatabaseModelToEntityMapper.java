package com.recursia.popularmovies.data.mappers;

import com.recursia.popularmovies.data.models.CastDatabaseModel;
import com.recursia.popularmovies.domain.models.Cast;
import com.recursia.popularmovies.utils.Mapper;

public class CastDatabaseModelToEntityMapper extends Mapper<CastDatabaseModel, Cast> {
    @Override
    public Cast transform(CastDatabaseModel castDatabaseModel) {
        Cast cast = new Cast();
        cast.setCastId(castDatabaseModel.getCastId());
        cast.setCharacter(castDatabaseModel.getCharacter());
        cast.setCreditId(castDatabaseModel.getCreditId());
        cast.setGender(castDatabaseModel.getGender());
        cast.setId(castDatabaseModel.getId());
        cast.setProfilePath(castDatabaseModel.getProfilePath());
        cast.setMovieId(castDatabaseModel.getMovieId());
        cast.setName(castDatabaseModel.getName());
        cast.setOrder(castDatabaseModel.getOrder());
        return cast;
    }
}
