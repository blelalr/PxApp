package com.android.pxapp.data.model


import com.google.gson.annotations.SerializedName

data class BannerResult(
    @SerializedName("banners")
    val banners: List<Banner> = listOf()
)