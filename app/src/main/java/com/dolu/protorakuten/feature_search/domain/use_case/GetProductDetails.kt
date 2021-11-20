package com.dolu.protorakuten.feature_search.domain.use_case

import com.dolu.protorakuten.core.domain.repository.CoreRepository
import com.dolu.protorakuten.core.domain.model.ProductDetails
import com.dolu.protorakuten.core.util.Resource
import io.reactivex.Observable
import io.reactivex.Single

class GetProductDetails(
    private val repository: CoreRepository
) {
    operator fun invoke(id: Long): Observable<Resource<ProductDetails>> {
        return repository.getProductDetails(id)
    }
}