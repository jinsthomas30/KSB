package com.example.ksb.datalist.presenter.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ksb.R
import com.example.ksb.datalist.domain.model.Post
import com.example.ksb.datalist.presenter.state.UiState
import com.example.ksb.datalist.presenter.viewModel.DataViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreen(onItemClick: (Post) -> Unit, dataViewModel: DataViewModel = hiltViewModel()) {
    val uiState by dataViewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    // a string without arguments
                    text = stringResource(R.string.app_name)
                )
            })
        }
    )
    { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (val state = uiState) {
                is UiState.Loading -> Loading()
                is UiState.Success -> DataList(state.data, onItemClick)
                is UiState.Error -> ErrorMessage(state.message)
            }
        }

    }
}

@Composable
fun DataList(data: List<Post>, onItemClick: (Post) -> Unit) {
    Column {
        LazyColumn() {
            items(data) { data ->
                DataItem(data, onItemClick)
            }
        }
    }

}

@Composable
fun DataItem(post: Post, onItemClick: (Post) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)

            .clickable { onItemClick(post) }) {
        Text(text = post.title, fontWeight = FontWeight.Bold, color = Color.Blue)
        Spacer(Modifier.height(8.dp))
        Text(text = post.body, color = Color.Blue)
    }

}

@Composable
fun ErrorMessage(message: String) {
    Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
        Text(message, color = Color.Red, textAlign = TextAlign.Center)
    }
}

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("loading_indicator"), Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
