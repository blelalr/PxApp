package com.android.pxapp.data.model


import com.google.gson.annotations.SerializedName

data class MessageResult(
    @SerializedName("messages")
    var messages: List<Message> = listOf()
)