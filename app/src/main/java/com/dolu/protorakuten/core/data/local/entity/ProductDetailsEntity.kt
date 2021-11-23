package com.dolu.protorakuten.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dolu.protorakuten.core.domain.model.GlobalRating
import com.dolu.protorakuten.core.domain.model.ProductDetails
import com.dolu.protorakuten.core.domain.model.RakutenImage

@Entity
data class ProductDetailsEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val categories: List<String>,
    val description: String,
    val globalRating: GlobalRating,
    val images: List<RakutenImage>,
    val newBestPrice: Double,
    val quality: String,
    val salePrice: Double,
    val seller: String,
    val sellerComment: String,
    val type: String,
    val usedBestPrice: Double
) {
    fun toProductDetails(): ProductDetails {
        return ProductDetails(
            id = id,
            categories = categories,
            title = title,
            newBestPrice = newBestPrice,
            salePrice = salePrice,
            usedBestPrice = usedBestPrice,
            description = description,
            globalRating = globalRating,
            images = images,
            quality = quality,
            seller = seller,
            sellerComment = sellerComment,
            type = type
        )
    }
}