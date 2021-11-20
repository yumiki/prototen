package com.dolu.protorakuten.feature_search.data.remote.dto

data class ProductInfoDto(
    val categories: List<String>,
    val description: String,
    val globalRating: GlobalRatingDto,
    val headline: String,
    val images: List<ImageDto>,
    val newBestPrice: Double,
    val productId: Long,
    val quality: String,
    val salePrice: Double,
    val seller: SellerDto,
    val sellerComment: String,
    val type: String,
    val usedBestPrice: Int
)