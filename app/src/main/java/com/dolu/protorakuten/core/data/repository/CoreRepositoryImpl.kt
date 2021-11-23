package com.dolu.protorakuten.core.data.repository

import android.util.Log
import com.dolu.protorakuten.core.data.local.ProductDetailsDao
import com.dolu.protorakuten.core.data.remote.ProductApi
import com.dolu.protorakuten.core.domain.model.ProductDetails
import com.dolu.protorakuten.core.domain.repository.CoreRepository
import com.dolu.protorakuten.core.util.Resource
import io.reactivex.Observable
import retrofit2.HttpException
import javax.inject.Inject

class CoreRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val productDetailsDao: ProductDetailsDao
) : CoreRepository {
    override fun getProductDetails(id: Long): Observable<Resource<ProductDetails>> {
        return Observable.create { emitter ->
            emitter.onNext(Resource.Loading())
            val cachedData = productDetailsDao.getProductDetailsById(id)?.toProductDetails()

            emitter.onNext(Resource.Loading(cachedData))

            try {
                val networkData = productApi
                    .getItem(id)
                    .map {
                        it.toProductDetails()
                    }
                    .blockingGet()
                Log.i(TAG, "Api result: $networkData")
                productDetailsDao.deleteProductDetailsById(networkData.id)
                productDetailsDao.insertProductDetails(networkData.toProductEntity())
            } catch (e: HttpException) {
                emitter.onNext(
                    Resource.Error(
                        message = "Oulala ca marche pas ${e.code()}",
                        data = cachedData
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, "e: ${e.localizedMessage}")
                e.printStackTrace()
                emitter.onNext(
                    Resource.Error(
                        message = "Oulala ca marche pas ${e.localizedMessage}",
                        data = cachedData
                    )
                )
            }
            val newData = productDetailsDao.getProductDetailsById(id)?.toProductDetails()
            Log.e(TAG, "updated data for $id => $newData")
            if (newData != null) {
                emitter.onNext(Resource.Success(newData))
            } else {
                Log.e(TAG, "No data available for $id fallback to mock data")
                emitter.onNext(
                    Resource.Success(
                        productDetailsDao
                            .getAllProductDetails()
                            .firstOrNull()
                            ?.toProductDetails()
                    )
                )
            }
            emitter.onComplete()
        }
    }

    companion object {
        val TAG = CoreRepository::class.java.simpleName
    }
}