package com.recursia.popularmovies.domain.models

/**
 * Copyright Alexander Silinsky 2020
 * Date 10.04.2020
 * Trailer entity model
 */
data class Trailer(
        var movieId: Int = 0,
        var id: String? = null,
        var key: String? = null,
        var name: String? = null,
        var site: String? = null,
        var size: Int = 0,
        var type: String? = null
)
