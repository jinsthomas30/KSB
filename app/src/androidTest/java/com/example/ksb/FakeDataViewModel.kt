package com.example.ksb

import androidx.lifecycle.ViewModel
import com.example.ksb.datalist.presenter.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeDataViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> get() = _uiState

    fun setUiState(state: UiState) {
        _uiState.value = state
    }
}
