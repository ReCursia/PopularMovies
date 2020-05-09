package com.recursia.popularmovies.data.network

import com.recursia.popularmovies.data.models.*

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("3/discover/movie?api_key=$API_KEY")
    fun discoverMovies(
            @Query("sort_by") sortBy: String,
            @Query("page") page: Int,
            @Query("vote_count.gte") voteCount: Int,
            @Query("language") language: String
    ): Single<DiscoverMoviesResponse>

    @GET("3/movie/{id}/videos?api_key=$API_KEY")
    fun getMovieTrailersById(@Path("id") id: Int, @Query("language") language: String): Single<MovieTrailersResponse>

    @GET("3/movie/{id}?api_key=$API_KEY")
    fun getMovieById(@Path("id") id: Int, @Query("language") language: String): Single<MovieDatabaseModel>

    @GET("3/movie/{id}/credits?api_key=$API_KEY")
    fun getMovieCreditsById(@Path("id") id: Int): Single<CreditsResponse>

    @GET("3/movie/{id}/recommendations?api_key=$API_KEY")
    fun getMovieRecommendations(@Path("id") id: Int, @Query("page") page: Int, @Query("language") language: String): Single<DiscoverMoviesResponse>

    @GET("3/search/movie?api_key=$API_KEY")
    fun getMoviesByQuery(@Query("query") query: String, @Query("page") page: Int, @Query("language") language: String): Single<DiscoverMoviesResponse>

    @GET("3/movie/{id}/reviews?api_key=$API_KEY")
    fun getMovieReviews(@Path("id") id: Int, @Query("page") page: Int): Single<ReviewsResponse>

    companion object {
        const val API_KEY = "b158b97e42698b2da042bf942864f95f"
    }
}
