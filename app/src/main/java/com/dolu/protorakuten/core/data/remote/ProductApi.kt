package com.dolu.protorakuten.core.data.remote

import com.dolu.protorakuten.core.data.remote.dto.ProductDetailsDto
import com.dolu.protorakuten.feature_search.data.remote.dto.ProductDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {
    @GET("/products/details")
    fun getItem(@Query("id") itemId: Long): Single<ProductDetailsDto>
}