package com.dolu.protorakuten.feature_search.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dolu.protorakuten.feature_search.data.remote.dto.BuyboxDto
import com.dolu.protorakuten.feature_search.domain.model.Buybox

@Entity
data class ProductEntity (
    @PrimaryKey val id: Long,
    val buybox: Buybox,
    val categoryRef: Int,
    val title: String,
    val imagesUrls: List<String>,
    val nbReviews: Int,
    val newBestPrice: Double,
    val reviewsAverageNote: Double,
    val usedBestPrice: Double
)
