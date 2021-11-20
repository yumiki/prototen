package com.dolu.protorakuten.feature_search.presentation

import com.dolu.protorakuten.core.domain.model.Product

data class SearchState(
    val searchResultProducts: List<Product>? = emptyList(),
    val isLoading: Boolean = false
)