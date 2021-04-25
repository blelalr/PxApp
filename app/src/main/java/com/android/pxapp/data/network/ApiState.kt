package com.android.pxapp.data.network

sealed class ApiState {
    object Loading: ApiState()

    data class Success<T>(val data: T): ApiState()

    data class Failed<T>(val errorCode: T, val error: T): ApiState()

}