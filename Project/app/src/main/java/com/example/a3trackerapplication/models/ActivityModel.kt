package com.example.a3trackerapplication.models

sealed class ActivityModel {
    data class TaskData(
        val title: String,
        val assignedToUserName: String,
        val creatorUserName: String,
        val creatorUserImage: String?,
        val createdTime: Long,
        val groupName: String
    ) : ActivityModel()

    data class GroupData(
        val groupName: String,
        val joiningDate: Long,
        val invitingUserName: String,
        val invitingUserImage: String?
    ) : ActivityModel()

    data class UserData(
        var name: String,
        var mentorName: String,
        var mentorImage: String?,
        var departmentName: String,
        var location: String?,
        var imageUrl: String?,
    ) : ActivityModel()
}