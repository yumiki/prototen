package com.dolu.protorakuten.feature_search.data.remote

import com.dolu.protorakuten.feature_search.data.remote.dto.ProductDto
import com.dolu.protorakuten.feature_search.data.remote.dto.SearchResultDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchApi {
    @GET("/products/search")
    fun search(@Query("keyword") keyword: String): Single<SearchResultDto>
    @GET("/products/details")
    fun getItem(@Query("id") itemId: String): Single<ProductDto>
}