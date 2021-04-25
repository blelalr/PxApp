package com.android.pxapp.data.model


import com.google.gson.annotations.SerializedName

data class PaymentQrData(
    @SerializedName("result")
    val paymentQrResult: PaymentQrResult = PaymentQrResult(),
    @SerializedName("status_code")
    val statusCode: Int = 0
)