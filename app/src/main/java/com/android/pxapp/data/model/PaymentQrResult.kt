package com.android.pxapp.data.model


import com.google.gson.annotations.SerializedName

data class PaymentQrResult(
    @SerializedName("qr_code")
    val qrCode: String = ""
)