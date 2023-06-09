package com.example.a3trackerapplication.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EditTaskRequest (
    var taskId: Long,
    var title: String,
    var description: String,
    var assignedToUserId: Long,
    var priority: TaskPriorities,
    var deadline: Long,
    var groupId: Long,
    var status: TaskStatus
)