package com.example.ksb.datalist.data.remote

import com.example.ksb.datalist.data.model.PostDtoItem
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getData(): List<PostDtoItem>
}