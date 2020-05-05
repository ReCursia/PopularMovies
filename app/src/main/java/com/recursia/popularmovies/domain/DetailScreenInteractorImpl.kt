package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.Review
import io.reactivex.Completable
import io.reactivex.Single

class DetailScreenInteractorImpl(
        private val moviesRepository: MoviesRepository,
        private val translateRepository: TranslateRepository
) : DetailScreenInteractor {

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

    override fun translateReview(review: Review, lang: String): Single<Review> {
        return translateRepository.translateText(review.text!!, lang)
                .map {
                    review.text = it
                    review
                }
    }
}
