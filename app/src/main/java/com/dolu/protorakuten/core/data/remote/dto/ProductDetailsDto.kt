package com.dolu.protorakuten.core.data.remote.dto

import com.dolu.protorakuten.core.domain.model.ImageData
import com.dolu.protorakuten.core.domain.model.ProductDetails
import com.dolu.protorakuten.core.domain.model.RakutenImage
import com.dolu.protorakuten.core.domain.model.Size
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDetailsDto(
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
    val usedBestPrice: Double
) {
    fun toProductDetails() : ProductDetails {
        return ProductDetails(
            productId,
            categories,
            description,
            globalRating.toGlobalRating(),
            headline,
            images.map { image ->
                val links = image.imagesUrls.entry.map {
                    ImageData(
                        Size.valueOf(it.size),
                        it.url
                    )
                }.associateBy(
                    { it.size },
                    { it.link }
                ).toMap()

                RakutenImage(
                    links,
                    id = image.id
                )
            },
            newBestPrice,
            quality,
            salePrice,
            seller.login,
            sellerComment,
            type,
            usedBestPrice
        )
    }
}