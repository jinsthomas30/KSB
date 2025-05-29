package com.example.ksb.datalist.presenter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ksb.datalist.domain.usecase.DataUseCase
import com.example.ksb.datalist.presenter.state.NetworkState
import com.example.ksb.datalist.presenter.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(val dataUseCase: DataUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    var uiState: StateFlow<UiState> = _uiState

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            try {
                dataUseCase().collect { networkState ->
                    _uiState.value = when (networkState) {
                        is NetworkState.Loading -> UiState.Loading
                        is NetworkState.Success -> UiState.Success(networkState.data ?: emptyList())
                        is NetworkState.Error -> UiState.Error(
                            networkState.message ?: "Unknown error"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun setUiState(state: UiState) {
        _uiState.value = state
    }
}