package com.dolu.protorakuten.feature_search.data.remote.dto

import com.dolu.protorakuten.feature_search.domain.model.Buybox
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BuyboxDto(
    val advertQuality: String,
    val advertType: String,
    val saleCrossedPrice: Double?,
    val salePercentDiscount: Int?,
    val salePrice: Double
) {
    fun toBuybox() = Buybox(
        salePrice = salePrice
    )
}