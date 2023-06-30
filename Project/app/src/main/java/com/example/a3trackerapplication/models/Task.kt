package com.example.a3trackerapplication.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Task(
    var taskId: Long,
    var title: String,
    var description: String,
    var assignedToUserId: Long,
    var assignedToUserName: String,
    var creatorUserId: Long,
    var creatorUserName: String,
    var createdTime: Long,
    var priority: TaskPriorities,
    var deadline: Long,
    var groupId: Long,
    var groupName: String,
    var status: TaskStatus,
    var progress: Int
): Comparable<Task> {

    override fun compareTo(other: Task): Int {
        val statusComparison = status.compareTo(other.status)
        if (statusComparison != 0) {
            return statusComparison
        }
        return priority.compareTo(other.priority)
    }
}