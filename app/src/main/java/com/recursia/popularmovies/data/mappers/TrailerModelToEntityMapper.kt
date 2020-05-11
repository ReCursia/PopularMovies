package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.TrailerModel
import com.recursia.popularmovies.domain.models.Trailer
import com.recursia.popularmovies.utils.Mapper

class TrailerModelToEntityMapper : Mapper<TrailerModel, Trailer>() {
    override fun transform(trailerModel: TrailerModel): Trailer {
        val trailer = Trailer()
        trailer.id = trailerModel.id
        trailer.key = trailerModel.key
        trailer.movieId = trailerModel.movieId
        trailer.name = trailerModel.name
        trailer.type = trailerModel.type
        trailer.site = trailerModel.site
        trailer.size = trailerModel.size
        return trailer
    }
}
