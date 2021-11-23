package com.dolu.protorakuten.core.data.remote.dto

import com.dolu.protorakuten.core.domain.model.GlobalRating
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GlobalRatingDto(
    val nbReviews: Int,
    val score: Double
) {
    fun toGlobalRating(): GlobalRating {
        return GlobalRating(
            nbReviews = nbReviews,
            score = score
        )
    }
}