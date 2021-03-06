package com.dolu.protorakuten.feature_search.domain.use_case

import com.dolu.protorakuten.core.domain.model.Product
import com.dolu.protorakuten.core.util.Resource
import com.dolu.protorakuten.feature_search.domain.repository.SearchRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * This usecase validate user inputs, trim the result to delete trailing spaces
 */
class GetSearchResultProducts@Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(query: String): Flowable<Resource<List<Product>>> {
        if (query.isBlank()) {
            return Flowable.empty()
        }
        return  repository.search(query.trim())
    }
}