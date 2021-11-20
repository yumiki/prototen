package com.dolu.protorakuten.feature_search.data.remote.dto

import com.dolu.protorakuten.feature_search.data.local.entity.SearchResultsEntity
import com.dolu.protorakuten.feature_search.domain.model.SearchResults
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResultDto(
    val title: String,
    val maxPageNumber: Int,
    val maxProductsPerPage: Int,
    val pageNumber: Int,
    val products: List<ProductDto>,
    val resultProductsCount: Int,
    val totalResultProductsCount: Int
) {
    fun toSearchResult() = SearchResults(
        title,
        products.map { it.toProduct() }
    )

    fun toSearchResultEntity(): SearchResultsEntity {
        return SearchResultsEntity(
            title = title,
            products = products.map { it.toProduct() },
            maxPageNumber = maxPageNumber,
            maxProductsPerPage = maxProductsPerPage,
            pageNumber = pageNumber,
            resultProductsCount = resultProductsCount,
            totalResultProductsCount = totalResultProductsCount
        )
    }
}