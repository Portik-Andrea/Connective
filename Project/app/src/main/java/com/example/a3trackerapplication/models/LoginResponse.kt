package com.example.a3trackerapplication.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse (
    var userId: Long,
    var token: String
)
