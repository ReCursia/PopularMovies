package com.recursia.popularmovies.domain.models

data class Trailer(
        var movieId: Int = 0,
        var id: String? = null,
        var key: String? = null,
        var name: String? = null,
        var site: String? = null,
        var size: Int = 0,
        var type: String? = null
)
