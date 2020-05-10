package com.recursia.popularmovies.data.repositories

import com.recursia.popularmovies.data.db.MovieDao
import com.recursia.popularmovies.data.network.MoviesApi
import com.recursia.popularmovies.domain.MoviesRepository
import com.recursia.popularmovies.domain.models.*
import com.recursia.popularmovies.domain.models.enums.MovieStatus
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class MoviesRepositoryImpl(
        private val movieDao: MovieDao,
        private val moviesApi: MoviesApi
//        private val movieDatabaseModelToEntityMapper: MovieDatabaseModelToEntityMapper,
//        private val discoverMovieResponseToMovieMapper: DiscoverMovieResponseToMovieMapper,
//        private val creditsResponseToCastMapper: CreditsResponseToCastMapper,
//        private val movieTrailersResponseToTrailersMapper: MovieTrailersResponseToTrailersMapper,
//        private val movieExtraDatabaseModelToEntityMapper: MovieExtraDatabaseModelToEntityMapper,
//        private val entityToMovieExtraDatabaseModelMapper: EntityToMovieExtraDatabaseModelMapper,
//        private val reviewsResponseToReviewMapper: ReviewsResponseToReviewMapper
) : MoviesRepository {

    //    override fun discoverMovies(sortBy: String, page: Int, voteCount: Int, language: String): Single<List<Movie>> {
//        return moviesApi.discoverMovies(sortBy, page, voteCount, language)
//                .subscribeOn(Schedulers.io())
//                .map { discoverMovieResponseToMovieMapper.transform(it) }
//    }
//
//    override val allFavoriteMovies: Flowable<List<Movie>>
//        get() = movieDao.allMovies
//                .subscribeOn(Schedulers.io())
//                .map { movieExtraDatabaseModelToEntityMapper.transform(it) }
//
//    override fun getMovieById(movieId: Int, language: String): Single<Movie> {
//        return movieDao.getMovieById(movieId)
//                .map { movieExtraDatabaseModelToEntityMapper.transform(it) }
//                .onErrorResumeNext { loadMovieFromNetwork(movieId, language) }
//                .subscribeOn(Schedulers.io())
//    }
//
//    private fun loadMovieFromNetwork(movieId: Int, language: String): Single<Movie> {
//        val movieSingle = moviesApi.getMovieById(movieId, language)
//                .map { movieDatabaseModelToEntityMapper.transform(it) }
//        val castListSingle = moviesApi.getMovieCreditsById(movieId)
//                .map { creditsResponseToCastMapper.transform(it) }
//        val trailerListSingle = moviesApi.getMovieTrailersById(movieId, language)
//                .map { movieTrailersResponseToTrailersMapper.transform(it) }
//        val reviewListSingle = moviesApi.getMovieReviews(movieId, REVIEWS_PAGE)
//                .map { reviewsResponseToReviewMapper.transform(it) }
//
//        return Single.zip(
//                movieSingle,
//                castListSingle,
//                trailerListSingle,
//                reviewListSingle,
//                Function4 { movie: Movie, cast: List<Cast>, trailers: List<Trailer>, reviews: List<Review> ->
//                    movie.casts = cast
//                    movie.trailers = trailers
//                    movie.reviews = reviews
//                    movie
//                })
//                .subscribeOn(Schedulers.io())
//    }
//
//    override fun getMoviesByQuery(query: String, page: Int, language: String): Single<List<Movie>> {
//        return moviesApi.getMoviesByQuery(query, page, language)
//                .subscribeOn(Schedulers.io())
//                .map { discoverMovieResponseToMovieMapper.transform(it) }
//    }
//
//    override fun getMovieRecommendations(movieId: Int, page: Int, language: String): Single<List<Movie>> {
//        return moviesApi.getMovieRecommendations(movieId, page, language)
//                .subscribeOn(Schedulers.io())
//                .map { discoverMovieResponseToMovieMapper.transform(it) }
//    }
//
//    override fun makeFavoriteMovie(movie: Movie): Completable {
//        return Completable.fromAction { movieDao.insertMovieExtra(entityToMovieExtraDatabaseModelMapper.transform(movie)) }
//                .doOnSubscribe { movie.isFavorite = true }
//                .subscribeOn(Schedulers.io())
//    }
//
//    override fun removeFavoriteMovie(movie: Movie): Completable {
//        return Completable.fromAction { movieDao.deleteMovieExtra(entityToMovieExtraDatabaseModelMapper.transform(movie)) }
//                .doOnSubscribe { movie.isFavorite = false }
//                .subscribeOn(Schedulers.io())
//    }
//
//    companion object {
//        private const val REVIEWS_PAGE = 1
//    }
    override fun getMoviesWithStatus(status: MovieStatus): Flowable<List<Movie>> {
        TODO("Not yet implemented")
    }

    override fun discoverMovies(sortBy: String, page: Int, voteCount: Int, language: String): Single<List<Movie>> {
        TODO("Not yet implemented")
    }

    private fun getMockedMovie(): Movie {
        return Movie(
                1,
                arrayListOf(
                        Genre(0, 0, "Drama"),
                        Genre(0, 0, "Fight"),
                        Genre(0, 0, "Just mock")
                ),
                arrayListOf(
                        Trailer()
                ),
                arrayListOf(
                        Cast(0, 0, null, null, 0, 0, "Lindsy Lohan"),
                        Cast(0, 0, null, null, 0, 0, "Lindsy Lohan"),
                        Cast(0, 0, null, null, 0, 0, "Lindsy Lohan"),
                        Cast(0, 0, null, null, 0, 0, "Lindsy Lohan"),
                        Cast(0, 0, null, null, 0, 0, "Lindsy Lohan")
                ),
                arrayListOf(
                        Review("Test", "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!"),
                        Review("Test", "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!"),
                        Review("Test", "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!"),
                        Review("Test", "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!" +
                                "This my lovely long review ahahahah look at this!!!!!!!!!!!!!!!!!!")

                ),
                MovieStatus.WANT_TO_WATCH,
                "This is Harry potter film" +
                        "This is Harry potter film" +
                        "This is Harry potter film" +
                        "This is Harry potter film" +
                        "This is Harry potter film",
                "",
                "23 sempt",
                "Harry Potter and Prince Half-Blood test",
                8.4,
                ""

        )
    }

    override fun getMovieById(movieId: Int, language: String): Single<Movie> {
        return Single.just(getMockedMovie())
    }

    override fun getMoviesByQuery(query: String, page: Int, language: String): Single<List<Movie>> {
        TODO("Not yet implemented")
    }

    override fun getMovieRecommendations(movieId: Int, page: Int, language: String): Single<List<Movie>> {
        return Single.just(arrayListOf(
                getMockedMovie(),
                getMockedMovie(),
                getMockedMovie(),
                getMockedMovie(),
                getMockedMovie(),
                getMockedMovie(),
                getMockedMovie(),
                getMockedMovie()))
    }

    override fun setMovieStatus(movie: Movie): Completable {
        TODO("Not yet implemented")
    }
}
