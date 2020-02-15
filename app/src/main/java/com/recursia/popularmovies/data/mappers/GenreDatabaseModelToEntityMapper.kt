package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.GenreDatabaseModel
import com.recursia.popularmovies.domain.models.Genre
import com.recursia.popularmovies.utils.Mapper

class GenreDatabaseModelToEntityMapper : Mapper<GenreDatabaseModel, Genre>() {
    override fun transform(genreDatabaseModel: GenreDatabaseModel): Genre {
        val genre = Genre()
        genre.id = genreDatabaseModel.id
        genre.movieId = genreDatabaseModel.movieId
        genre.name = genreDatabaseModel.name
        return genre
    }
}
