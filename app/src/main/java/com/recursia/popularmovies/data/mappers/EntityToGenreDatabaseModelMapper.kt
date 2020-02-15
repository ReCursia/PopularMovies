package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.GenreDatabaseModel
import com.recursia.popularmovies.domain.models.Genre
import com.recursia.popularmovies.utils.Mapper

class EntityToGenreDatabaseModelMapper : Mapper<Genre, GenreDatabaseModel>() {
    override fun transform(genre: Genre): GenreDatabaseModel {
        val genreDatabaseModel = GenreDatabaseModel()
        genreDatabaseModel.id = genre.id
        genreDatabaseModel.movieId = genre.movieId
        genreDatabaseModel.name = genre.name
        return genreDatabaseModel
    }
}
