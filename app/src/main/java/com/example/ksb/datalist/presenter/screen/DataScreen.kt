package com.example.ksb.datalist.presenter.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ksb.R
import com.example.ksb.datalist.domain.model.Post
import com.example.ksb.datalist.presenter.state.UiState
import com.example.ksb.datalist.presenter.viewModel.DataViewModel
import com.google.android.gms.tasks.OnSuccessListener


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreen(onItemClick: (Post) -> Unit, dataViewModel: DataViewModel = hiltViewModel()) {
    val uiState by dataViewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    // a string without arguments
                    text = stringResource(R.string.app_name)
                )
            }, scrollBehavior = scrollBehavior)
        }, modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    )
    { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LaunchedEffect(Unit) {
                dataViewModel.getData()
            }
            Column {
                OutlinedTextField(
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_close_24),
                                contentDescription = "clear button",
                                tint = colorResource(R.color.teal_200),
                                modifier = Modifier.clickable {
                                    searchQuery = ""
                                }
                            )
                        }
                    },
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search..") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                when (val state = uiState) {
                    is UiState.Loading -> Loading()
                    is UiState.Success -> {
                        val userData = state.data
                        val filteredItems = userData.filter {
                            it.title.contains(searchQuery, ignoreCase = true)
                        }
                        if (filteredItems.isEmpty()) {
                            ErrorMessage("NoData")
                        }
                        DataList(filteredItems, onItemClick)
                    }

                    is UiState.Error -> ErrorMessage(state.message)
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataList(data: List<Post>, onItemClick: (Post) -> Unit) {
    LazyColumn {
        items(data) { data ->
            DataItem(data, onItemClick)
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
