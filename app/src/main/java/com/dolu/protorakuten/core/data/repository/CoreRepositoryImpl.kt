package com.dolu.protorakuten.core.data.repository

import com.dolu.protorakuten.core.data.local.ProductDetailsDao
import com.dolu.protorakuten.core.data.remote.ProductApi
import com.dolu.protorakuten.core.domain.model.ProductDetails
import com.dolu.protorakuten.core.domain.repository.CoreRepository
import com.dolu.protorakuten.core.util.Resource
import io.reactivex.Observable
import io.reactivex.Single
import java.lang.Exception
import javax.inject.Inject

class CoreRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val productDetailsDao: ProductDetailsDao
) : CoreRepository{
    override fun getProductDetails(id: Long): Observable<Resource<ProductDetails>> {
        return Observable.create{ emitter ->
            emitter.onNext(Resource.Loading())
            val cachedData = productDetailsDao.getProductDetailsById(id)
            try {
                val networkData = productApi
                                    .getItem(id)
                                    .map {
                                        it.toProductDetails()
                                    }
            } catch (e: Exception){

            }
        }
    }
}