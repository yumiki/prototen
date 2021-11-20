package com.dolu.protorakuten.feature_search.domain.repository

import com.dolu.protorakuten.core.domain.model.Product
import com.dolu.protorakuten.core.util.Resource
import io.reactivex.Flowable

interface SearchRepository {
    fun search(query: String): Flowable<Resource<List<Product>>>
}