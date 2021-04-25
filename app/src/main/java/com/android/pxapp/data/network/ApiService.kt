package com.android.pxapp.data.network

import com.android.pxapp.data.model.BannerData
import com.android.pxapp.data.model.MessageData
import com.android.pxapp.data.model.PaymentQrData
import retrofit2.Response
import retrofit2.http.GET

@JvmSuppressWildcards
interface ApiService {

    @GET("v3/f6733f2d-82fc-43e7-b19d-d8381f0ab91e")
    suspend fun getBannersService(): Response<BannerData>

    @GET("v3/0f0488e1-e532-45e5-8033-bef5904359fe")
    suspend fun getMessagesService(): Response<MessageData>

    @GET("v3/8c29aeec-3ab4-4ac1-9b2e-e99652dbd155")
    suspend fun getPaymentQrCodeService(): Response<PaymentQrData>

}
