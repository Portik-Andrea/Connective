package com.example.a3trackerapplication.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    var id: Long,
    var lastName: String,
    var firstName: String,
    var email: String,
    var type: UserType,
    var departmentId: Long,
    var departmentName: String,
    var location: String?,
    var phoneNumber: String?,
    var imageUrl: String?,
    var mentorId: Long
)