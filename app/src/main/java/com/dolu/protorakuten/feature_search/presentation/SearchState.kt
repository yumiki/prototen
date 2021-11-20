package com.dolu.protorakuten.feature_search.presentation

import com.dolu.protorakuten.core.model.Product
import com.dolu.protorakuten.feature_search.domain.model.SearchResults

data class SearchState(
    val searchResultProducts: List<Product>? = emptyList(),
    val isLoading: Boolean = false
)