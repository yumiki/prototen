package com.dolu.protorakuten.core.domain.model

import com.dolu.protorakuten.feature_search.domain.model.Buybox
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    val buybox: Buybox,
    val categoryRef: Int,
    val title: String,
    val imagesUrls: List<String>,
    val review: GlobalRating,
    val newBestPrice: Double,
    val usedBestPrice: Double,
    val id: Long
)

val dummyProduct = Product(
    id = 0,
    usedBestPrice = 20.0,
    buybox = Buybox(salePrice = 0.0),
    newBestPrice = 0.0,
    categoryRef = 0,
    imagesUrls = listOf("https://fr.shopping.rakuten.com/photo/1673299896.jpg"),
    review = GlobalRating(
        0,
        4.2
    ),
    title = "Samsung de test"
)