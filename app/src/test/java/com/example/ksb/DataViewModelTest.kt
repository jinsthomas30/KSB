package com.example.ksb

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
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class DataViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()


    private lateinit var dataUseCase: DataUseCase
    private lateinit var viewModel: DataViewModel


    @Before
    fun setup() {
        dataUseCase = mockk()
    }

    @Test
    fun `uiState emits Success when data fetch successful`() = runTest {
        // Mock success
        coEvery { dataUseCase.invoke() } returns flow {
            emit(NetworkState.Loading())
            emit(NetworkState.Success(listOf(Post(1, "Test", "Body"))))
        }

        viewModel = DataViewModel(dataUseCase)

        viewModel.uiState.test {
            assert(awaitItem() is UiState.Loading)
            val successState = awaitItem() as UiState.Success
            assertEquals(1, successState.data.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uiState should emit Loading and Error when data fetch fails`() = runTest {
        val errorMessage = "Network error"
        coEvery { dataUseCase.invoke() } returns flow {
            emit(NetworkState.Loading())
            emit(NetworkState.Error(message = errorMessage))
        }

        viewModel = DataViewModel(dataUseCase)

        viewModel.uiState.test {

            assert(awaitItem() is UiState.Loading)
            val errorState = awaitItem() as UiState.Error
            assertEquals(errorMessage, errorState.message)

            // Optional: Cancel remaining emissions, to clean up the test
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uiState should emit susccessful result`() = runTest {

        coEvery { dataUseCase.invoke() } returns flow {
            emit(NetworkState.Loading())
            emit(NetworkState.Success(listOf(Post(1,"Jins","Thomas"))))
        }

        viewModel = DataViewModel(dataUseCase)

        viewModel.uiState.test {

            assert(awaitItem() is UiState.Loading)
            val successData = awaitItem() as UiState.Success
            assertEquals("Jins",successData.data.get(0).title)
            cancelAndIgnoreRemainingEvents()
        }
    }




}

