package com.example.a3trackerapplication.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Task(
    var taskId: Long,
    var title: String,
    var description: String,
    var assignedToUserId: Long,
    var assignedToUserName: String,
    var createdByUserId: Long,
    var createdByUserName: String,
    var createdTime: Long,
    var priority: TaskPriorities,
    var deadline: Long,
    //var projectId: Long,
    var status: TaskStatus,
    var progress: Int
)