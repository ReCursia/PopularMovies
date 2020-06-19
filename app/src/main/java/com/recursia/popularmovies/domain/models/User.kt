package com.recursia.popularmovies.domain.models

/**
 * Copyright Alexander Silinsky 2020
 * Date 10.04.2020
 * User entity model
 */
data class User(
        var username: String? = null,
        var email: String? = null,
        var profileImagePath: String? = null
)
