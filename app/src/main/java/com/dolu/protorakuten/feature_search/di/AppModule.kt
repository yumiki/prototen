package com.dolu.protorakuten.feature_search.di

import com.dolu.protorakuten.BuildConfig
import com.dolu.protorakuten.core.data.remote.ProductApi
import com.dolu.protorakuten.feature_search.data.remote.SearchApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    fun provideBaseUrl() = "https://4206121f-64a1-4256-a73d-2ac541b3efe4.mock.pstmn.io/"

    @Singleton
    @Provides
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideWebService(retrofit: Retrofit): SearchApi = retrofit.create(SearchApi::class.java)

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit): ProductApi = retrofit.create(ProductApi::class.java)
}