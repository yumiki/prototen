package com.dolu.protorakuten.feature_search.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImagesUrlsDto(
    val entry: List<ImageInfoDto>
)