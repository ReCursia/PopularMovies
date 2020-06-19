package com.recursia.popularmovies.domain.models

import com.recursia.popularmovies.domain.models.enums.ReviewStatus

data class Review(
    var author: String? = null,
    var text: String? = null,
    var id: String? = null,
    var url: String? = null,
    var status: ReviewStatus = ReviewStatus.UNKNOWN
)
