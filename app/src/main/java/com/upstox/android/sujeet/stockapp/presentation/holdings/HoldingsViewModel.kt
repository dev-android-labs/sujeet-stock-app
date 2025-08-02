package com.upstox.android.sujeet.stockapp.presentation.holdings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upstox.android.sujeet.stockapp.domain.usecase.GetHoldingsUseCase
import com.upstox.android.sujeet.stockapp.presentation.AppIntent
import com.upstox.android.sujeet.stockapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */
@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val getHoldingsUseCase: GetHoldingsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HoldingsUiState())
    val state: StateFlow<HoldingsUiState> = _state

    fun onIntent(intent: AppIntent) {
        when (intent) {
            is AppIntent.LoadHoldings -> loadHoldings()
            is AppIntent.ToggleSummary -> toggleSummary()
        }
    }

    private fun loadHoldings() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when (val result = getHoldingsUseCase()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            holdings = result.data.holdings,
                            summary = result.data
                        )
                    }
                }
                is Result.Error -> {
                    _state.update {
                        it.copy(isLoading = false, error = result.exception.message)
                    }
                }
            }
        }
    }

    private fun toggleSummary() {
        _state.update { it.copy(isExpanded = !it.isExpanded) }
    }
}