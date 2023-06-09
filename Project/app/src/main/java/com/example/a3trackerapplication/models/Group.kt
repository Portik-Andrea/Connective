package com.example.a3trackerapplication.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Group (
    val groupId: Long,
    val groupName: String
)