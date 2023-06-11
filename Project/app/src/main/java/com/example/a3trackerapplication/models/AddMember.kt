package com.example.a3trackerapplication.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddMember (
    var groupId: Long,
    var userId: Long
)