package com.example.ksb.datalist.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: Int,
    val title: String,
    val body: String
): Parcelable
