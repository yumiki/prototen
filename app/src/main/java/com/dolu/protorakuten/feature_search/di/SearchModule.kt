package com.dolu.protorakuten.feature_search.di

import android.app.Application
import androidx.room.Room
import com.dolu.protorakuten.feature_search.data.local.Converters
import com.dolu.protorakuten.feature_search.data.local.entity.ProductDatabase
import com.dolu.protorakuten.feature_search.data.local.entity.SearchResultsDatabase
import com.dolu.protorakuten.feature_search.data.remote.SearchApi
import com.dolu.protorakuten.feature_search.data.repository.SearchRepositoryImpl
import com.dolu.protorakuten.feature_search.data.util.MoshiParser
import com.dolu.protorakuten.feature_search.domain.repository.SearchRepository
import com.dolu.protorakuten.feature_search.domain.use_case.GetSearchResultProducts
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SearchModule {
    @Provides
    @Singleton
    fun provideGetSearchResultProductsUseCase(repository: SearchRepository): GetSearchResultProducts {
        return GetSearchResultProducts(repository)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(
        searchDatabase: SearchResultsDatabase,
        searchApi: SearchApi,
        productDatabase: ProductDatabase
    ) : SearchRepository {
        return SearchRepositoryImpl(
            searchApi,
            searchDatabase.dao,
            productDatabase.dao
        )
    }

    @Provides
    @Singleton
    fun provideSearchResultsDatabase(
        app: Application,
        moshi: Moshi): SearchResultsDatabase {
        return Room.databaseBuilder(
            app,
            SearchResultsDatabase::class.java,
            "search_db")
            .addTypeConverter(Converters(MoshiParser(moshi)))
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDatabase(
        app: Application,
        moshi: Moshi): ProductDatabase {
        return Room.databaseBuilder(
            app,
            ProductDatabase::class.java,
            "product_db")
            //.addTypeConverter(Converters(MoshiParser(moshi)))
            .build()
    }

    @Singleton
    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder().build()
}