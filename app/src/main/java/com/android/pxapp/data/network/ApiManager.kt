package com.android.pxapp.data.network

import android.util.Log
import com.android.pxapp.data.model.BannerData
import com.android.pxapp.data.model.MessageData
import com.android.pxapp.data.model.PaymentQrData
import com.android.pxapp.util.Prefs.deleteMessageSet
import com.android.pxapp.util.prefs


object ApiManager {
    private val apiService = ApiServiceBuilder().apiService

    suspend fun getBannersFromNetwork(): ApiState {
        return try {
            val result = apiService.getBannersService()
            if (result.isSuccessful && result.body() != null) {
                ApiState.Success(result.body() as BannerData)
            } else {
                ApiState.Failed(
                    result.code(),
                    result.message()
                )
            }
        } catch (e: Exception) {
            ApiState.Failed(
                "",
                e.message
            )
        }
    }

    suspend fun getMessagesFromNetwork(): ApiState {
        return try {
            val result = apiService.getMessagesService()
            if (result.isSuccessful && result.body() != null) {
                ApiState.Success(result.body() as MessageData)
            } else {
                ApiState.Failed(
                    result.code(),
                    result.message()
                )
            }
        } catch (e: Exception) {
            ApiState.Failed(
                "",
                e.message
            )
        }
    }

    suspend fun getPaymentQrCodeFromNetwork(): ApiState {
        return try {
            val result = apiService.getPaymentQrCodeService()
            if (result.isSuccessful && result.body() != null) {
                ApiState.Success(result.body() as PaymentQrData)
            } else {
                ApiState.Failed(
                    result.code(),
                    result.message()
                )
            }
        } catch (e: Exception) {
            ApiState.Failed(
                "",
                e.message
            )
        }
    }

}