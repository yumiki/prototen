package com.dolu.protorakuten.core.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RakutenImage(
    val links: Map<Size, String>,
    val id: Long? = null
)

enum class Size {
    ORIGINAL,
    SMALL,
    MEDIUM,
    LARGE
}

@JsonClass(generateAdapter = true)
data class ImageData(
    val size: Size,
    val link: String
)
