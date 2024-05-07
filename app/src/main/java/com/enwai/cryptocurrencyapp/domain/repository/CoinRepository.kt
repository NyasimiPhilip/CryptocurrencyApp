package com.enwai.cryptocurrencyapp.domain.repository

import com.enwai.cryptocurrencyapp.data.remote.dto.CoinDetailDto
import com.enwai.cryptocurrencyapp.data.remote.dto.CoinDto

interface CoinRepository {

    suspend fun getCoins(): List<CoinDto>
    suspend fun getCoinById(coinId: String): CoinDetailDto
}