package com.android.pxapp.data.repository

import com.android.pxapp.data.network.ApiManager
import com.android.pxapp.data.network.ApiState

class AppRepo {

    suspend fun loadMessageData(): ApiState {
        return ApiManager.getMessagesFromNetwork()
    }

    suspend fun loadBannersData(): ApiState {
        return ApiManager.getBannersFromNetwork()
    }

    suspend fun loadPaymentQrCodeData(): ApiState {
        return ApiManager.getPaymentQrCodeFromNetwork()
    }

}