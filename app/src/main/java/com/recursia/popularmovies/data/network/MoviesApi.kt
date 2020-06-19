package com.recursia.popularmovies.data.network

import com.recursia.popularmovies.data.models.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Copyright Alexander Silinsky 2020
 * Date 10.04.2020
 * This interface describes movies API to work with
 */
interface MoviesApi {

    /**
     * Method to get movie trailers by id
     * @param id movieId
     * @param language language
     *
     * @return movieTrailers response
     */
    @GET("3/movie/{id}/videos?api_key=$API_KEY")
    fun getMovieTrailersById(@Path("id") id: Int, @Query("language") language: String): Single<MovieTrailersResponse>

    /**
     * Method to get movie by id
     * @param id movieId
     * @param language language
     *
     * @return movie
     */
    @GET("3/movie/{id}?api_key=$API_KEY")
    fun getMovieById(@Path("id") id: Int, @Query("language") language: String): Single<MovieModel>

    /**
     * Method to get movie credits by id
     * @param id movieId
     *
     * @return credits
     */
    @GET("3/movie/{id}/credits?api_key=$API_KEY")
    fun getMovieCreditsById(@Path("id") id: Int): Single<CreditsResponse>

    /**
     * Get movie recommendations
     * @param id movieId
     * @param page page
     * @param language language
     *
     * @return discover movies response
     */
    @GET("3/movie/{id}/recommendations?api_key=$API_KEY")
    fun getMovieRecommendations(@Path("id") id: Int, @Query("page") page: Int, @Query("language") language: String): Single<DiscoverMoviesResponse>

    /**
     * Get movies by query
     * @param query text query
     * @param page page
     * @param language language
     *
     * @return discover movies reponse
     */
    @GET("3/search/movie?api_key=$API_KEY")
    fun getMoviesByQuery(@Query("query") query: String, @Query("page") page: Int, @Query("language") language: String): Single<DiscoverMoviesResponse>

    /**
     * Get movies reviews
     * @param id movieId
     * @param page page index
     *
     * @return reviews response
     */
    @GET("3/movie/{id}/reviews?api_key=$API_KEY")
    fun getMovieReviews(@Path("id") id: Int, @Query("page") page: Int): Single<ReviewsResponse>

    /**
     * Get movies now playing
     * @param language language
     * @param page page
     *
     * @return discover movies reponse
     */
    @GET("3/movie/now_playing?api_key=$API_KEY")
    fun getMoviesNowPlaying(@Query("language") language: String, @Query("page") page: Int): Single<DiscoverMoviesResponse>

    /**
     * Get movies popular
     * @param language language
     * @param page page
     *
     * @return discover movies reponse
     */
    @GET("3/movie/popular?api_key=$API_KEY")
    fun getMoviesPopular(@Query("language") language: String, @Query("page") page: Int): Single<DiscoverMoviesResponse>

    /**
     * Get movies top rated
     * @param language language
     * @param page page
     *
     * @return discover movies reponse
     */
    @GET("3/movie/top_rated?api_key=$API_KEY")
    fun getMoviesTopRated(@Query("language") language: String, @Query("page") page: Int): Single<DiscoverMoviesResponse>

    /**
     * Get movies upcoming
     * @param language language
     * @param page page
     *
     * @return discover movies reponse
     */
    @GET("3/movie/upcoming?api_key=$API_KEY")
    fun getMoviesUpcoming(@Query("language") language: String, @Query("page") page: Int): Single<DiscoverMoviesResponse>

    /**
     * Get genre movies
     * @param genreId genre id
     * @param language language
     * @param page page
     *
     * @return discover genre movies response
     */
    @GET("3/discover/movie?api_key=$API_KEY")
    fun getGenreMovies(
            @Query("with_genres") genreId: Int,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Single<DiscoverMoviesResponse>

    /**
     * Get genres
     * @param language language
     *
     * @return genres
     */
    @GET("3/genre/movie/list?api_key=$API_KEY")
    fun getGenres(@Query("language") language: String): Single<GenresResponse>

    companion object {
        //api key to access database
        const val API_KEY = "b158b97e42698b2da042bf942864f95f"
    }
}
