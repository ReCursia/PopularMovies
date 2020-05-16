package com.recursia.popularmovies.domain.models

data class User(
        var username: String? = null,
        var registrationDate: String? = null,
        var profileImagePath: String? = null
)
