package com.example.a3trackerapplication.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
data class LoginResponse (
    @SerializedName("userId")
    var userId: Int,
    @SerializedName("token")
    var token: String
)
