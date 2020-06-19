package com.recursia.popularmovies.domain.models

import com.recursia.popularmovies.domain.models.enums.ReviewStatus

/**
 * Copyright Alexander Silinsky 2020
 * Date 10.04.2020
 * Review entity model
 */
data class Review(
        var author: String? = null,
        var text: String? = null,
        var id: String? = null,
        var url: String? = null,
        var status: ReviewStatus = ReviewStatus.UNKNOWN
)
