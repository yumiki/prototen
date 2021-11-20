package com.dolu.protorakuten.core.domain.model

import com.dolu.protorakuten.core.data.remote.dto.GlobalRatingDto
import com.dolu.protorakuten.core.data.remote.dto.ImageDto
import com.dolu.protorakuten.core.data.remote.dto.SellerDto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDetails(
    val categories: List<String>,
    val description: String,
    val globalRating: GlobalRatingDto,
    val title: String,
    val images: List<ImageDto>,
    val newBestPrice: Double,
    val id: Long,
    val quality: String,
    val salePrice: Double,
    val seller: SellerDto,
    val sellerComment: String,
    val type: String,
    val usedBestPrice: Int
)