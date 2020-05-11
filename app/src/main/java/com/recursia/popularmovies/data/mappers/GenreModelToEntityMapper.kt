package com.recursia.popularmovies.data.mappers

import com.recursia.popularmovies.data.models.GenreModel
import com.recursia.popularmovies.domain.models.Genre
import com.recursia.popularmovies.utils.Mapper

class GenreModelToEntityMapper : Mapper<GenreModel, Genre>() {
    override fun transform(genreDatabaseModel: GenreModel): Genre {
        val genre = Genre()
        genre.id = genreDatabaseModel.id
        genre.movieId = genreDatabaseModel.movieId
        genre.name = genreDatabaseModel.name
        return genre
    }
}
