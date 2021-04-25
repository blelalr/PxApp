package com.android.pxapp.data.model


import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("msg")
    val msg: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("ts")
    val ts: String = ""
)