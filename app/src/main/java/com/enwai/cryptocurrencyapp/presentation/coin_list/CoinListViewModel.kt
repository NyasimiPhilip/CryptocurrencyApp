package com.enwai.cryptocurrencyapp.presentation.coin_list

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enwai.cryptocurrencyapp.common.Resource
import com.enwai.cryptocurrencyapp.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

// HiltViewModel annotation to indicate that this ViewModel will be provided by Hilt
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase // Injecting GetCoinsUseCase dependency
) : ViewModel() {
    // Mutable state for holding the current state of the coin list UI
    private val _state = mutableStateOf(CoinListState())
    val state: State<CoinListState> = _state // Exposing immutable state to external observers

    init {
        getCoins() // Fetching coins when ViewModel is initialized
    }

    // Function to fetch the list of coins
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun getCoins() {
        getCoinsUseCase().onEach { result ->
            // Handling different states of the resource (Loading, Success, Error)
            when (result) {
                is Resource.Error -> {
                    // Updating state with error message if an error occurs
                    _state.value = CoinListState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    // Updating state to indicate loading state
                    _state.value = CoinListState(isLoading = true)
                }
                is Resource.Success -> {
                    // Updating state with list of coins if fetching is successful
                    _state.value = CoinListState(coins = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope) // Launching the flow within the ViewModel's scope
    }
}
