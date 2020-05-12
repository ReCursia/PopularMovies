package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.Review
import io.reactivex.Completable
import io.reactivex.Single

class DetailScreenInteractorImpl(
        private val accountRepository: AccountRepository,
        private val moviesRepository: MoviesRepository
) : DetailScreenInteractor {
    override fun getMovieById(movieId: Int, language: String): Single<Movie> {
        return accountRepository.getMovieById(movieId)
                .switchIfEmpty(moviesRepository.getMovieById(movieId, language))
    }

    override fun setMovieStatus(movie: Movie): Completable {
        return accountRepository.setMovieStatus(movie)
    }

    override fun getMovieRecommendations(movieId: Int, page: Int, language: String): Single<List<Movie>> {
        return moviesRepository.getMovieRecommendations(movieId, page, language)
    }

    override fun translateReview(review: Review, lang: String): Single<Review> {
        TODO("Not yet implemented")
    }
}
