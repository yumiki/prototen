package com.dolu.protorakuten.feature_search.data.remote.dto

import com.squareup.moshi.JsonClass

/**
 * Corresponding to an entry in product detail json
 */
@JsonClass(generateAdapter = true)
data class ImageInfoDto(
    val size: String,
    val url: String
)