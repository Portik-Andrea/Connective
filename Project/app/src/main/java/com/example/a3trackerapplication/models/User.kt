package com.example.a3trackerapplication.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    var id: Long,
    var lastName: String,
    var firstName: String,
    var email: String,
    var type: UserType,
    var departmentId: Long,
    var location: String?,
    var phoneNumber: String?,
    var imageUrl: String?,
    var mentorId: Long
)