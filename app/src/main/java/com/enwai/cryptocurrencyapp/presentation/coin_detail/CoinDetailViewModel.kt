package com.enwai.cryptocurrencyapp.presentation.coin_detail

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enwai.cryptocurrencyapp.common.Constants
import com.enwai.cryptocurrencyapp.common.Resource
import com.enwai.cryptocurrencyapp.domain.use_case.get_coin.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

// HiltViewModel annotation to indicate that this ViewModel will be provided by Hilt
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase, // Injecting GetCoinUseCase dependency
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    // Mutable state for holding the current state of the coin list UI
    private val _state = mutableStateOf(CoinDetailState())
    val state: State<CoinDetailState> = _state // Exposing immutable state to external observers

    init {
        // Fetching coin details when ViewModel is initialized
        savedStateHandle.get<String>(Constants.COIN_PARAM_ID)?.let { coinId ->
            getCoin(coinId)
        }
    }

    // Function to fetch the details of a coin
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun getCoin(coinId: String) {
        getCoinUseCase(coinId).onEach { result ->
            // Handling different states of the resource (Loading, Success, Error)
            when (result) {
                is Resource.Error -> {
                    // Updating state with error message if an error occurs
                    _state.value = CoinDetailState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    // Updating state to indicate loading state
                    _state.value = CoinDetailState(isLoading = true)
                }
                is Resource.Success -> {
                    // Updating state with details of the coin if fetching is successful
                    _state.value = CoinDetailState(coin = result.data)
                }
            }
        }.launchIn(viewModelScope) // Launching the flow within the ViewModel's scope
    }
}
