package com.android.pxapp.data.model


import com.google.gson.annotations.SerializedName

data class BannerData(
    @SerializedName("result")
    val bannerResult: BannerResult = BannerResult(),
    @SerializedName("status_code")
    val statusCode: Int = 0
)