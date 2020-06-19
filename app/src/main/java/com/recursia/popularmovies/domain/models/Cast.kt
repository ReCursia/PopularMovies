package com.recursia.popularmovies.domain.models

data class Cast(
    var movieId: Int = 0,
    var castId: Int = 0,
    var character: String? = null,
    var creditId: String? = null,
    var gender: Int = 0,
    var id: Int = 0,
    var name: String? = null,
    var order: Int = 0,
    var profilePath: String? = null
)
