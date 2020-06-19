package com.recursia.popularmovies.domain.models

import com.recursia.popularmovies.domain.models.enums.MovieStatus

/**
 * Copyright Alexander Silinsky 2020
 * Date 10.04.2020
 * Movie entity model
 */
data class Movie(
        var id: Int = 0,
        var genres: List<Genre> = ArrayList(),
        var trailers: List<Trailer> = ArrayList(),
        var casts: List<Cast> = ArrayList(),
        var reviews: List<Review> = ArrayList(),
        var status: MovieStatus = MovieStatus.UNKNOWN,
        var overview: String? = null,
        var posterPath: String? = null,
        var releaseDate: String? = null,
        var title: String? = null,
        var voteAverage: Double = 0.0,
        var backdropPath: String? = null
)
