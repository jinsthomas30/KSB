package com.example.ksb.datalist.domain.repository

import com.example.ksb.datalist.data.model.PostDtoItem
import com.example.ksb.datalist.domain.model.Post
import com.example.ksb.datalist.presenter.state.NetworkState
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getData(): Flow<NetworkState<List<Post>>>
}