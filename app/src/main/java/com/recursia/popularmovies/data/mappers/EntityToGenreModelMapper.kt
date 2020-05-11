package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.GenreModel
import com.recursia.popularmovies.domain.models.Genre
import com.recursia.popularmovies.utils.Mapper

class EntityToGenreModelMapper : Mapper<Genre, GenreModel>() {
    override fun transform(genre: Genre): GenreModel {
        val genreDatabaseModel = GenreModel()
        genreDatabaseModel.id = genre.id
        genreDatabaseModel.movieId = genre.movieId
        genreDatabaseModel.name = genre.name!!
        return genreDatabaseModel
    }
}
