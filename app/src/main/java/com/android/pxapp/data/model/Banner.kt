package com.android.pxapp.data.model


import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("image")
    val image: String = "",
    @SerializedName("target_url")
    val targetUrl: String = "",
    @SerializedName("title")
    val title: String = ""
)