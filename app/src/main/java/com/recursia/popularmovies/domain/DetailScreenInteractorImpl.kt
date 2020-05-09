package com.recursia.popularmovies.domain

import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.Review
import io.reactivex.Completable
import io.reactivex.Single

class DetailScreenInteractorImpl : DetailScreenInteractor {
    override fun getMovieById(movieId: Int, language: String): Single<Movie> {
        TODO("Not yet implemented")
    }

    override fun setMovieStatus(movie: Movie): Completable {
        TODO("Not yet implemented")
    }

    override fun getMovieRecommendations(movieId: Int, page: Int, language: String): Single<List<Movie>> {
        TODO("Not yet implemented")
    }

    override fun translateReview(review: Review, lang: String): Single<Review> {
        TODO("Not yet implemented")
    }
}
