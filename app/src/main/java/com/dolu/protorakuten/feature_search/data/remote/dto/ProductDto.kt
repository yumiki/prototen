package com.dolu.protorakuten.feature_search.data.remote.dto

import com.dolu.protorakuten.core.model.Product
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDto(
    val buybox: BuyboxDto,
    val categoryRef: Int,
    val headline: String,
    val id: Long,
    val imagesUrls: List<String>,
    val nbReviews: Int,
    val newBestPrice: Double,
    val reviewsAverageNote: Double,
    val usedBestPrice: Double
) {
    fun toProduct() = Product(
        id = id,
        title = headline,
        usedBestPrice = usedBestPrice,
        buybox = buybox.toBuybox(),
        imagesUrls = imagesUrls,
        nbReviews = nbReviews,
        newBestPrice = newBestPrice,
        reviewsAverageNote = reviewsAverageNote,
        categoryRef = categoryRef
    )
}