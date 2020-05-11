package com.recursia.popularmovies.data.repositories

import com.recursia.popularmovies.data.mappers.*
import com.recursia.popularmovies.data.network.MoviesApi
import com.recursia.popularmovies.domain.MoviesRepository
import com.recursia.popularmovies.domain.models.Cast
import com.recursia.popularmovies.domain.models.Movie
import com.recursia.popularmovies.domain.models.Review
import com.recursia.popularmovies.domain.models.Trailer
import com.recursia.popularmovies.domain.models.enums.Category
import io.reactivex.Single
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers

class MoviesRepositoryImpl(
        private val moviesApi: MoviesApi,
        private val discoverMovieResponseToMovieMapper: DiscoverMovieResponseToMovieMapper,
        private val creditsResponseToCastMapper: CreditsResponseToCastMapper,
        private val movieTrailersResponseToTrailersMapper: MovieTrailersResponseToTrailersMapper,
        private val reviewsResponseToReviewMapper: ReviewsResponseToReviewMapper,
        private val movieModelToEntityMapper: MovieModelToEntityMapper
) : MoviesRepository {

    override fun getMovieById(movieId: Int, language: String): Single<Movie> {
        return loadMovieFromNetwork(movieId, language)
    }

    private fun loadMovieFromNetwork(movieId: Int, language: String): Single<Movie> {
        val movieSingle = moviesApi.getMovieById(movieId, language)
                .map { movieModelToEntityMapper.transform(it) }
        val castListSingle = moviesApi.getMovieCreditsById(movieId)
                .map { creditsResponseToCastMapper.transform(it) }
        val trailerListSingle = moviesApi.getMovieTrailersById(movieId, language)
                .map { movieTrailersResponseToTrailersMapper.transform(it) }
        val reviewListSingle = moviesApi.getMovieReviews(movieId, PAGE)
                .map { reviewsResponseToReviewMapper.transform(it) }

        return Single.zip(
                movieSingle,
                castListSingle,
                trailerListSingle,
                reviewListSingle,
                Function4 { movie: Movie, cast: List<Cast>, trailers: List<Trailer>, reviews: List<Review> ->
                    movie.casts = cast
                    movie.trailers = trailers
                    movie.reviews = reviews
                    movie
                }
        )
    }

    override fun getMoviesByQuery(query: String, page: Int, language: String): Single<List<Movie>> {
        return moviesApi.getMoviesByQuery(query, page, language)
                .map { discoverMovieResponseToMovieMapper.transform(it) }
                .subscribeOn(Schedulers.io())
    }

    override fun getMovieRecommendations(movieId: Int, page: Int, language: String): Single<List<Movie>> {
        return moviesApi.getMovieRecommendations(movieId, page, language)
                .map { discoverMovieResponseToMovieMapper.transform(it) }
                .subscribeOn(Schedulers.io())
    }

    override fun getMoviesWithCategory(category: Category, language: String): Single<List<Movie>> {
        val observable = when (category) {
            Category.UPCOMING -> moviesApi.getMoviesUpcoming(language, PAGE)
            Category.POPULAR -> moviesApi.getMoviesPopular(language, PAGE)
            Category.NOW_PLAYING -> moviesApi.getMoviesNowPlaying(language, PAGE)
            Category.TOP_RATED -> moviesApi.getMoviesTopRated(language, PAGE)
        }
        return observable
                .map { discoverMovieResponseToMovieMapper.transform(it) }
                .subscribeOn(Schedulers.io())
    }

    companion object {
        private const val PAGE = 1
    }
}
