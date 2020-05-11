package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.CastModel
import com.recursia.popularmovies.domain.models.Cast
import com.recursia.popularmovies.utils.Mapper

class CastModelToEntityMapper : Mapper<CastModel, Cast>() {
    override fun transform(castDatabaseModel: CastModel): Cast {
        val cast = Cast()
        cast.castId = castDatabaseModel.castId
        cast.character = castDatabaseModel.character
        cast.creditId = castDatabaseModel.creditId
        cast.gender = castDatabaseModel.gender
        cast.id = castDatabaseModel.id
        cast.profilePath = castDatabaseModel.profilePath
        cast.movieId = castDatabaseModel.movieId
        cast.name = castDatabaseModel.name
        cast.order = castDatabaseModel.order
        return cast
    }
}
