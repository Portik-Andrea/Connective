package com.example.a3trackerapplication.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
data class LoginRequest(
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String
    )