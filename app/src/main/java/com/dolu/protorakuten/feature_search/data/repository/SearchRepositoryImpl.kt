package com.dolu.protorakuten.feature_search.data.repository

import android.util.Log
import com.dolu.protorakuten.core.domain.model.Product
import com.dolu.protorakuten.core.util.Resource
import com.dolu.protorakuten.feature_search.data.local.ProductDao
import com.dolu.protorakuten.feature_search.data.local.SearchResultsDao
import com.dolu.protorakuten.feature_search.data.remote.SearchApi
import com.dolu.protorakuten.feature_search.domain.repository.SearchRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchRepositoryImpl @Inject
constructor(
    private val searchApi: SearchApi,
    private val searchDao: SearchResultsDao,
    private val productDao: ProductDao
): SearchRepository {
    override fun search(query: String): Flowable<Resource<List<Product>>> {
        return Flowable.create({ emitter ->
            emitter.onNext(Resource.Loading())
            val searchResults = searchDao.getSearchResult(query)?.toSearchResults()
            Log.i(TAG, "cache result: $searchResults")
            emitter.onNext(Resource.Loading(searchResults?.products))

            try {
                val remoteSearchResultsDto = searchApi
                    .search(query)
                    .blockingGet()

                Log.i(TAG, "Api result: $remoteSearchResultsDto")

                searchDao.deleteSearchByTitle(query)
                searchDao.insertSearchResults(remoteSearchResultsDto.toSearchResultEntity())

            } catch (e: HttpException) {
                emitter.onNext(
                    Resource.Error(
                    message = "Oulala ca marche pas ${e.code()}",
                    data = searchResults?.products
                ))
            } catch (e: IOException) {
                emitter.onNext(
                    Resource.Error(
                    message = "Oulala ca marche pas fatal ${e.localizedMessage}",
                    data = searchResults?.products
                ))
            } catch (e: RuntimeException) {
                Log.e(TAG, "Error occurred: ${e.localizedMessage}")
                e.printStackTrace()
                emitter.onNext(
                    Resource.Error(
                        message = "Oulala ca marche pas run ${e.cause?.localizedMessage}",
                        data = searchResults?.products
                    ))
            }

            val brandNewSearchResultsProducts = searchDao.getSearchResult(query)?.toSearchResults()?.products
            emitter.onNext(Resource.Success(brandNewSearchResultsProducts))
        }, BackpressureStrategy.BUFFER)
    }

    companion object {
        val TAG: String = SearchRepositoryImpl::class.java.simpleName
    }
}