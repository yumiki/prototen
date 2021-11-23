package com.dolu.protorakuten.core.domain.model

import com.dolu.protorakuten.core.data.local.entity.ProductDetailsEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDetails(
    val id: Long,
    val categories: List<String>,
    val description: String,
    val globalRating: GlobalRating,
    val title: String,
    val images: List<RakutenImage>,
    val newBestPrice: Double,
    val quality: String,
    val salePrice: Double,
    val seller: String,
    val sellerComment: String,
    val type: String,
    val usedBestPrice: Double
) {
    fun toProductEntity(): ProductDetailsEntity {
        return ProductDetailsEntity(
            id = id,
            categories = categories,
            description = description,
            globalRating = globalRating,
            title = title,
            images = images,
            type = type,
            sellerComment = sellerComment,
            seller = seller,
            quality = quality,
            usedBestPrice = usedBestPrice,
            salePrice = salePrice,
            newBestPrice = newBestPrice,
        )
    }
}