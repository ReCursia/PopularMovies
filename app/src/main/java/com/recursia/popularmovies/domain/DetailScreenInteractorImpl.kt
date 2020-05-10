package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.Review
import io.reactivex.Completable
import io.reactivex.Single

class DetailScreenInteractorImpl(private val repository: MoviesRepository) : DetailScreenInteractor {
    override fun getMovieById(movieId: Int, language: String): Single<Movie> {
        return repository.getMovieById(movieId, language)
    }

    override fun setMovieStatus(movie: Movie): Completable {
        //TODO implement
        return Completable.complete()
    }

    override fun getMovieRecommendations(movieId: Int, page: Int, language: String): Single<List<Movie>> {
        return repository.getMovieRecommendations(movieId, page, language)
    }

    override fun translateReview(review: Review, lang: String): Single<Review> {
        TODO("Not yet implemented")
    }
}
