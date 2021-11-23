package com.dolu.protorakuten.core.data.remote.dto

import com.dolu.protorakuten.feature_search.data.remote.dto.ImagesUrlsDto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageDto(
    val id: Long,
    val imagesUrls: ImagesUrlsDto
)