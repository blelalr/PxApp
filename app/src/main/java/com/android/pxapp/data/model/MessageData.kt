package com.android.pxapp.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MessageData(
    @SerializedName("result")
    val messageResult: MessageResult = MessageResult(),
    @SerializedName("status_code")
    val statusCode: Int = -1
): Serializable