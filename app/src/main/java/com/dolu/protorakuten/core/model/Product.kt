package com.dolu.protorakuten.core.model

import com.dolu.protorakuten.feature_search.data.local.entity.ProductEntity
import com.dolu.protorakuten.feature_search.domain.model.Buybox
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    val buybox: Buybox,
    val categoryRef: Int,
    val title: String,
    val imagesUrls: List<String>,
    val nbReviews: Int,
    val newBestPrice: Double,
    val reviewsAverageNote: Double,
    val usedBestPrice: Double,
    val id: Long
) {
    fun toProductEntity(): ProductEntity {
        return ProductEntity(
            id = id,
            title = title,
            imagesUrls = imagesUrls,
            nbReviews = nbReviews,
            categoryRef = categoryRef,
            reviewsAverageNote = reviewsAverageNote,
            newBestPrice = newBestPrice,
            buybox = buybox,
            usedBestPrice = usedBestPrice
        )
    }
}

val dummyProduct = Product(
    id = 0,
    usedBestPrice = 20.0,
    buybox = Buybox(salePrice = 0.0),
    newBestPrice = 0.0,
    reviewsAverageNote = 0.0,
    categoryRef = 0,
    imagesUrls = listOf("https://fr.shopping.rakuten.com/photo/1673299896.jpg"),
    nbReviews = 0,
    title = "Samsung de test"

    )