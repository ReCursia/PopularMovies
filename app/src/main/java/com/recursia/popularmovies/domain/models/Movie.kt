package com.recursia.popularmovies.domain.models

class Movie {
    var isFavorite = false
    var budget = 0
    lateinit var genres: List<Genre>
    lateinit var trailers: List<Trailer>
    lateinit var casts: List<Cast>
    var id = 0
    var originalLanguage: String? = null
    var originalTitle: String? = null
    var overview: String? = null
    var popularity = 0.0
    var posterPath: String? = null
    var releaseDate: String? = null
    var runtime = 0
    var status: String? = null
    var tagline: String? = null
    var title: String? = null
    var isVideo = false
    var voteAverage = 0.0
    var voteCount = 0
    var backdropPath: String? = null
}
