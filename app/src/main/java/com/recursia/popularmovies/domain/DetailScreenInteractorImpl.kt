package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie

import io.reactivex.Completable
import io.reactivex.Single


//TODO delegate?
class DetailScreenInteractorImpl(private val moviesRepository: MoviesRepository) : DetailScreenInteractor {

    override fun getMovieRecommendations(movieId: Int, page: Int, language: String): Single<List<Movie>> {
        return moviesRepository.getMovieRecommendations(movieId, page, language)
    }

    override fun getMovieById(movieId: Int, language: String): Single<Movie> {
        return moviesRepository.getMovieById(movieId, language)
    }

    override fun makeFavoriteMovie(movie: Movie): Completable {
        return moviesRepository.makeFavoriteMovie(movie)
    }

    override fun removeFavoriteMovie(movie: Movie): Completable {
        return moviesRepository.removeFavoriteMovie(movie)
    }

}
