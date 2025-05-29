package com.example.ksb.datalist.domain.usecase

import com.example.ksb.datalist.domain.model.Post
import com.example.ksb.datalist.domain.repository.DataRepository
import com.example.ksb.datalist.presenter.state.NetworkState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class DataUseCase @Inject constructor(val dataRepository: DataRepository) {

    operator fun invoke():Flow<NetworkState<List<Post>>> = dataRepository.getData()
}