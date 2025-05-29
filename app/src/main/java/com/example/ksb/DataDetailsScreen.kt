package com.example.ksb

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ksb.datalist.domain.model.Post

@Composable
fun DataDetailsScreen(post: String?){

    println(" Id vale "+ post)
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val padding = innerPadding
    }
}