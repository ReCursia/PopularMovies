package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.TrailerModel
import com.recursia.popularmovies.domain.models.Trailer
import com.recursia.popularmovies.utils.Mapper

class EntityToTrailerModelMapper : Mapper<Trailer, TrailerModel>() {
    override fun transform(trailer: Trailer): TrailerModel {
        val trailerDatabaseModel = TrailerModel()
        trailerDatabaseModel.id = trailer.id
        trailerDatabaseModel.key = trailer.key
        trailerDatabaseModel.movieId = trailer.movieId
        trailerDatabaseModel.name = trailer.name
        trailerDatabaseModel.site = trailer.site
        trailerDatabaseModel.size = trailer.size
        trailerDatabaseModel.type = trailer.type
        return trailerDatabaseModel
    }
}
