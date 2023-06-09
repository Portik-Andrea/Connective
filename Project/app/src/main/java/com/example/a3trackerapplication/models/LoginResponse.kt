package com.example.a3trackerapplication.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
data class LoginResponse (
    @SerializedName("token")
    var token: String,
    @SerializedName("type")
    var userType: String
)
