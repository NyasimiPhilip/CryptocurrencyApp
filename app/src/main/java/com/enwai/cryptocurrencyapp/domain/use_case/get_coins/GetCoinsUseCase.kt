package com.enwai.cryptocurrencyapp.domain.use_case.get_coins

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.enwai.cryptocurrencyapp.common.Resource
import com.enwai.cryptocurrencyapp.data.remote.dto.toCoin
import com.enwai.cryptocurrencyapp.domain.model.Coin
import com.enwai.cryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCoins().map{it.toCoin()}
            emit(Resource.Success(coins))

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}

