package com.recursia.popularmovies.presentation.models

import java.io.Serializable

data class SectionItem(
        val fileName: String,
        val description: String
) : Serializable
