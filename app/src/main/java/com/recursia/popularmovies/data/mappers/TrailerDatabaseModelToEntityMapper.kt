package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.TrailerDatabaseModel
import com.recursia.popularmovies.domain.models.Trailer
import com.recursia.popularmovies.utils.Mapper

class TrailerDatabaseModelToEntityMapper : Mapper<TrailerDatabaseModel, Trailer>() {
    override fun transform(trailerDatabaseModel: TrailerDatabaseModel): Trailer {
        val trailer = Trailer()
        trailer.id = trailerDatabaseModel.id
        trailer.key = trailerDatabaseModel.key
        trailer.movieId = trailerDatabaseModel.movieId
        trailer.name = trailerDatabaseModel.name
        trailer.type = trailerDatabaseModel.type
        trailer.site = trailerDatabaseModel.site
        trailer.size = trailerDatabaseModel.size
        return trailer
    }
}
