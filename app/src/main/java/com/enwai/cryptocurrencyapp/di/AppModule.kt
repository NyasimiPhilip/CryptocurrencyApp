package com.enwai.cryptocurrencyapp.di

import com.enwai.cryptocurrencyapp.common.Constants
import com.enwai.cryptocurrencyapp.data.remote.CoinPaprikaApi
import com.enwai.cryptocurrencyapp.data.repository.CoinRepositoryImpl
import com.enwai.cryptocurrencyapp.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesPaprikaApi(): CoinPaprikaApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)
    }
    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinPaprikaApi): CoinRepository {
        return CoinRepositoryImpl(api)
    }
}

