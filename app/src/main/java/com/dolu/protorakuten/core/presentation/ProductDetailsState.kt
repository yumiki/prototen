package com.dolu.protorakuten.core.presentation

import com.dolu.protorakuten.core.domain.model.ProductDetails

data class ProductDetailsState(
    val productDetails: ProductDetails? = null,
    val isLoading: Boolean = false
)