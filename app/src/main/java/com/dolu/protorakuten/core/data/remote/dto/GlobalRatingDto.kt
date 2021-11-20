package com.dolu.protorakuten.core.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GlobalRatingDto(
    val nbReviews: Int,
    val score: Double
)