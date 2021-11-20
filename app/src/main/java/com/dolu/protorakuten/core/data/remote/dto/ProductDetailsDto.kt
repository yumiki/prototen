package com.dolu.protorakuten.core.data.remote.dto

import com.dolu.protorakuten.core.domain.model.ProductDetails
import com.dolu.protorakuten.core.domain.model.RakutenImage

data class ProductDetailsDto(
    val categories: List<String>,
    val description: String,
    val globalRating: GlobalRatingDto,
    val headline: String,
    val images: List<RakutenImage>,
    val newBestPrice: Double,
    val productId: Long,
    val quality: String,
    val salePrice: Double,
    val seller: SellerDto,
    val sellerComment: String,
    val type: String,
    val usedBestPrice: Int
) {
    fun toProductDetails() : ProductDetails {
        return ProductDetails(
            categories ,
            description,
            globalRating,
            headline,
            emptyList(),
            newBestPrice,
            productId,
            quality,
            salePrice,
            seller,
            sellerComment,
            type,
            usedBestPrice
        )
    }
}