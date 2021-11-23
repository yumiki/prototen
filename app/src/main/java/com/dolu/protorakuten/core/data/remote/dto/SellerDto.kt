package com.dolu.protorakuten.core.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SellerDto(
    val id: Long,
    val login: String
)