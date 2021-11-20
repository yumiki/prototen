package com.dolu.protorakuten.feature_search.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Buybox(
    val advertQuality: String? = null,
    val advertType: String? = null,
    val saleCrossedPrice: Int? = null,
    val salePercentDiscount: Int? = null,
    val salePrice: Double
)
