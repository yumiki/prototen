package com.dolu.protorakuten.feature_search.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dolu.protorakuten.core.domain.model.Product
import com.dolu.protorakuten.feature_search.domain.model.SearchResults

@Entity
class SearchResultsEntity (
    @PrimaryKey val title: String,
    val maxPageNumber: Int,
    val maxProductsPerPage: Int,
    val pageNumber: Int,
    val products: List<Product>,
    val resultProductsCount: Int,
    val totalResultProductsCount: Int
) {
    fun toSearchResults() = SearchResults(title, products)
}