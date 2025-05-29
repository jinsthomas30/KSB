package com.example.ksb.datalist.data.repository

import com.example.ksb.datalist.data.model.PostDtoItem
import com.example.ksb.datalist.data.model.toPostDtoItem
import com.example.ksb.datalist.data.remote.ApiService
import com.example.ksb.datalist.domain.model.Post
import com.example.ksb.datalist.domain.repository.DataRepository
import com.example.ksb.datalist.presenter.state.NetworkState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(val apiService: ApiService): DataRepository {
    override fun getData(): Flow<NetworkState<List<Post>>> = flow{
        emit(NetworkState.Loading())
        try{
            val data = apiService.getData().map {it.toPostDtoItem()}
            emit(NetworkState.Success(data))
        }catch (e: Exception){
            emit(NetworkState.Error(message = e.message?:"Unknown Error"))
        }
    }

}