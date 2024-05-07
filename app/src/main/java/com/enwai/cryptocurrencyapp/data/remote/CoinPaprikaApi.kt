package com.enwai.cryptocurrencyapp.data.remote

import com.enwai.cryptocurrencyapp.data.remote.dto.CoinDetailDto
import com.enwai.cryptocurrencyapp.data.remote.dto.CoinDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {
   @GET("/v1/coins")
   suspend fun getCoins(): List<CoinDto>

   @GET("/vi/coins/{coinId}")
   suspend fun getCoinById(@Path("coinId") coinId: String): CoinDetailDto
}