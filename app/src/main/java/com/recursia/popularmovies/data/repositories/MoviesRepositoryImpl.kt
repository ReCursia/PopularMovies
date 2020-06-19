package com.recursia.popularmovies.data.repositories

import com.recursia.popularmovies.data.mappers.*
import com.recursia.popularmovies.data.network.MoviesApi
import com.recursia.popularmovies.domain.MoviesRepository
import com.recursia.popularmovies.domain.models.*
import com.recursia.popularmovies.domain.models.enums.Category
import io.reactivex.Single
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers

/**
 * Copyright Alexander Silinsky 2020
 * Date 10.04.2020
 * Class used for retrieving movies, reviews and etc. from TMDB
 */
class MoviesRepositoryImpl(
        private val moviesApi: MoviesApi, //movies APi
        private val discoverMovieResponseToMovieMapper: DiscoverMovieResponseToMovieMapper, // mapper
        private val creditsResponseToCastMapper: CreditsResponseToCastMapper, // mapper
        private val movieTrailersResponseToTrailersMapper: MovieTrailersResponseToTrailersMapper, //mapper
        private val reviewsResponseToReviewMapper: ReviewsResponseToReviewMapper, // mapper
        private val movieModelToEntityMapper: MovieModelToEntityMapper, // mapper
        private val genresResponseToEntityMapper: GenresResponseToEntityMapper // mapper
) : MoviesRepository {

    /**
     * Method to get movie by id
     * @param id movieId
     * @param language language
     *
     * @return movie
     */
    override fun getMovieById(movieId: Int, language: String): Single<Movie> {
        return loadMovieFromNetwork(movieId, language)
                .subscribeOn(Schedulers.io())
    }

    /**
     * Function to load movie from network
     * @param movieId movie id
     * @param language language
     *
     * @return movie or onError
     */
    private fun loadMovieFromNetwork(movieId: Int, language: String): Single<Movie> {
        val movieSingle = moviesApi.getMovieById(movieId, language)
                .map { movieModelToEntityMapper.transform(it) }
        val castListSingle = moviesApi.getMovieCreditsById(movieId)
                .map { creditsResponseToCastMapper.transform(it) }
        val trailerListSingle = moviesApi.getMovieTrailersById(movieId, language)
                .map { movieTrailersResponseToTrailersMapper.transform(it) }
        val reviewListSingle = moviesApi.getMovieReviews(movieId, REVIEWS_PAGE)
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
        ).subscribeOn(Schedulers.io())
    }

    /**
     * Get movies by query
     * @param query text query
     * @param page page
     * @param language language
     *
     * @return discover movies reponse
     */
    override fun getMoviesByQuery(query: String, page: Int, language: String): Single<List<Movie>> {
        return moviesApi.getMoviesByQuery(query, page, language)
                .map { discoverMovieResponseToMovieMapper.transform(it) }
                .subscribeOn(Schedulers.io())
    }

    /**
     * Get movie recommendations
     * @param id movieId
     * @param page page
     * @param language language
     *
     * @return discover movies response
     */
    override fun getMovieRecommendations(movieId: Int, page: Int, language: String): Single<List<Movie>> {
        return moviesApi.getMovieRecommendations(movieId, page, language)
                .map { discoverMovieResponseToMovieMapper.transform(it) }
                .subscribeOn(Schedulers.io())
    }

    /**
     * Function to get movies with specified category
     * @param category category
     * @param page movies page
     * @param language language
     *
     * @return list of movies or onError
     */
    override fun getMoviesWithCategory(category: Category, page: Int, language: String): Single<List<Movie>> {
        val observable = when (category) {
            Category.UPCOMING -> moviesApi.getMoviesUpcoming(language, page)
            Category.POPULAR -> moviesApi.getMoviesPopular(language, page)
            Category.NOW_PLAYING -> moviesApi.getMoviesNowPlaying(language, page)
            Category.TOP_RATED -> moviesApi.getMoviesTopRated(language, page)
        }
        return observable
                .map { discoverMovieResponseToMovieMapper.transform(it) }
                .subscribeOn(Schedulers.io())
    }

    /**
     * Get genre movies
     * @param genreId genre id
     * @param language language
     * @param page page
     *
     * @return discover genre movies response
     */
    override fun getGenreMovies(genre: Genre, page: Int, language: String): Single<List<Movie>> {
        return moviesApi.getGenreMovies(genre.id, language, page)
                .map { discoverMovieResponseToMovieMapper.transform(it) }
                .subscribeOn(Schedulers.io())
    }

    /**
     * Get genres
     * @param language language
     *
     * @return genres
     */
    override fun getGenres(language: String): Single<List<Genre>> {
        return moviesApi.getGenres(language)
                .map { genresResponseToEntityMapper.transform(it) }
                .subscribeOn(Schedulers.io())
    }

    companion object {
        private const val REVIEWS_PAGE = 1 // reviews page
    }
}
