package com.example.ksb.datalist.presenter.state

import android.os.Message

sealed class NetworkState<T>(val data : T? = null, val message: String? = null) {
    class Loading<T>(data:T?=null): NetworkState<T>(data)
    class Success<T>(data:T): NetworkState<T>(data)
    class Error<T>(data:T?=null,message: String?): NetworkState<T>(data,message)
}