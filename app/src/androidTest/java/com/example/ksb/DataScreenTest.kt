package com.example.ksb

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.ksb.datalist.domain.model.Post
import com.example.ksb.datalist.domain.usecase.DataUseCase
import com.example.ksb.datalist.presenter.screen.DataScreen
import com.example.ksb.datalist.presenter.state.UiState
import com.example.ksb.datalist.presenter.viewModel.DataViewModel
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DataScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var dataViewModel: DataViewModel
    private lateinit var dataUseCase: DataUseCase

    @Before
    fun setup() {
        dataUseCase = mockk()
        dataViewModel = DataViewModel(dataUseCase)
    }

    @Test
    fun dataScreen_displaysLoadingState() {
        composeTestRule.setContent {
            DataScreen(dataViewModel = dataViewModel, onItemClick = {})
        }
        dataViewModel.setUiState(UiState.Loading)

        composeTestRule
            .onNode(hasTestTag("loading_indicator"))
            .assertIsDisplayed()
    }

    @Test
    fun dataScreen_displaysErrorState() {
        composeTestRule.setContent {
            DataScreen(dataViewModel = dataViewModel, onItemClick = {})
        }

        val errorMessage = "An error occurred"
        dataViewModel.setUiState(UiState.Error(errorMessage))

        composeTestRule
            .onNodeWithText(errorMessage)
            .assertIsDisplayed()
    }

    @Test
    fun dataScreen_displaySuccessState() {
        composeTestRule.setContent {
            DataScreen(dataViewModel = dataViewModel, onItemClick = {})
        }
        val post = listOf(Post(1, "Test Title", "Test Body"))
        dataViewModel.setUiState(UiState.Success(post))

        composeTestRule.onNodeWithText("Test Title").assertIsDisplayed()
    }
}
