package com.example.a3trackerapplication.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewTaskRequest(
    var title: String,
    var description: String,
    var assignedToUserId: Long,
    var groupId: Long,
    var priority: String,
    var deadline: Long,
    var status: String
)