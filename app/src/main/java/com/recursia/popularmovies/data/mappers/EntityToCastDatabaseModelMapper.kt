package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.CastDatabaseModel
import com.recursia.popularmovies.domain.models.Cast
import com.recursia.popularmovies.utils.Mapper

class EntityToCastDatabaseModelMapper : Mapper<Cast, CastDatabaseModel>() {
    override fun transform(cast: Cast): CastDatabaseModel {
        val castDatabaseModel = CastDatabaseModel()
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
