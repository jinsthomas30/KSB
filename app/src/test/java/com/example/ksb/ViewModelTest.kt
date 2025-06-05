package com.example.ksb

import android.provider.ContactsContract
import app.cash.turbine.test
import com.example.ksb.datalist.domain.model.Post
import com.example.ksb.datalist.domain.usecase.DataUseCase
import com.example.ksb.datalist.presenter.state.NetworkState
import com.example.ksb.datalist.presenter.state.UiState
import com.example.ksb.datalist.presenter.viewModel.DataViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import retrofit2.http.GET
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var  viewModel: DataViewModel
    private lateinit var  dataUseCase: DataUseCase

    @Before
    fun setup(){
        dataUseCase = mockk()
    }

    @Test
    fun `uistate emit success when data fetch`() = runTest {
        coEvery { dataUseCase.invoke() } returns flow {
            emit(NetworkState.Loading())
            emit(NetworkState.Success(listOf(Post(1,"title","body"))))
        }

        viewModel = DataViewModel(dataUseCase)

        viewModel.uiState.test {
            assert(awaitItem() is UiState.Loading)
            val data = awaitItem() as UiState.Success
            assertEquals(1,data.data.size)
            cancelAndIgnoreRemainingEvents()
        }
    }
}