package com.dolu.protorakuten.core.domain.repository

import com.dolu.protorakuten.core.domain.model.ProductDetails
import com.dolu.protorakuten.core.util.Resource
import io.reactivex.Observable
import io.reactivex.Single

interface CoreRepository {
    fun getProductDetails(id: Long): Observable<Resource<ProductDetails>>
}