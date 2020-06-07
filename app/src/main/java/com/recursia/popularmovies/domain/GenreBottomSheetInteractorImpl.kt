package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Genre
import com.recursia.popularmovies.domain.models.Movie
import io.reactivex.Single

class GenreBottomSheetInteractorImpl(
        private val moviesRepository: MoviesRepository
) : GenresBottomSheetInteractor {
    override fun getGenreMovies(genre: Genre, page: Int, language: String): Single<List<Movie>> {
        return moviesRepository.getGenreMovies(genre, page, language)
    }

    override fun getGenres(language: String): Single<List<Genre>> {
        return moviesRepository.getGenres(language)
    }
}
