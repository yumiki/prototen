package com.dolu.protorakuten.feature_search.domain.model

import com.dolu.protorakuten.core.model.Product

data class SearchResults(
    val title: String,
    val products: List<Product>
)
