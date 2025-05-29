package com.example.ksb.datalist.presenter.state

import com.example.ksb.datalist.data.model.PostDtoItem
import com.example.ksb.datalist.domain.model.Post

sealed class UiState {
    object Loading : UiState()
    data class Success(val data: List<Post>) : UiState()
    data class Error(val message : String) : UiState()
}