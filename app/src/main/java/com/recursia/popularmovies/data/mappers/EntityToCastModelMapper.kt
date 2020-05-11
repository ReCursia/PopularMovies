package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.CastModel
import com.recursia.popularmovies.domain.models.Cast
import com.recursia.popularmovies.utils.Mapper

class EntityToCastModelMapper : Mapper<Cast, CastModel>() {
    override fun transform(cast: Cast): CastModel {
        val castDatabaseModel = CastModel()
        castDatabaseModel.castId = cast.castId
        castDatabaseModel.character = cast.character
        castDatabaseModel.creditId = cast.creditId
        castDatabaseModel.gender = cast.gender
        castDatabaseModel.id = cast.id
        castDatabaseModel.name = cast.name
        castDatabaseModel.profilePath = cast.profilePath
        castDatabaseModel.movieId = cast.movieId
        castDatabaseModel.order = cast.order
        return castDatabaseModel
    }
}
