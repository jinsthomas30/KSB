package com.example.ksb.datalist.data.model

import com.example.ksb.datalist.domain.model.Post

data class PostDtoItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int?
)

fun PostDtoItem.toPostDtoItem() = Post(id = id, title = title, body = body)

