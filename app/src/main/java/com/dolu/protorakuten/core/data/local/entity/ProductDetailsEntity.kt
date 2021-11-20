package com.dolu.protorakuten.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dolu.protorakuten.core.domain.model.GlobalRating
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
    val productId: Long,
    val quality: String,
    val salePrice: Double,
    val seller: String,
    val sellerComment: String,
    val type: String,
    val usedBestPrice: Int
)