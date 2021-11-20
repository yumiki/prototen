package com.dolu.protorakuten.core.di

import android.app.Application
import androidx.room.Room
import com.dolu.protorakuten.core.data.local.Converters
import com.dolu.protorakuten.core.data.local.entity.ProductDetailsDatabase
import com.dolu.protorakuten.core.data.remote.ProductApi
import com.dolu.protorakuten.core.data.repository.CoreRepositoryImpl
import com.dolu.protorakuten.core.domain.repository.CoreRepository
import com.dolu.protorakuten.feature_search.data.util.MoshiParser
import com.dolu.protorakuten.feature_search.domain.repository.SearchRepository
import com.dolu.protorakuten.feature_search.domain.use_case.GetProductDetails
import com.dolu.protorakuten.feature_search.domain.use_case.GetSearchResultProducts
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductModule {
    @Provides
    @Singleton
    fun provideGetProductDetailsUseCase(repository: CoreRepository): GetProductDetails {
        return GetProductDetails(repository)
    }

    @Provides
    @Singleton
    fun provideCoreRepository(
        productDetailsDatabase: ProductDetailsDatabase,
        productApi: ProductApi
    ) : CoreRepository{
        return CoreRepositoryImpl(
            productApi,
            productDetailsDatabase.dao
        )
    }

    @Provides
    @Singleton
    fun provideProductDetailsDatabase(
        app: Application,
        moshi: Moshi
    ) : ProductDetailsDatabase {
        return Room.databaseBuilder(
            app,
            ProductDetailsDatabase::class.java,
            "product_details_db")
            .addTypeConverter(Converters(MoshiParser(moshi)))
            .build()
    }

}