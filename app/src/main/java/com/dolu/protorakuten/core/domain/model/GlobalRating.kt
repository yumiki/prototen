package com.dolu.protorakuten.core.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GlobalRating(
    val nbReviews: Int,
    val score: Double
)