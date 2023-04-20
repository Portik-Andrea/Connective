package com.example.a3trackerapplication

class TaskSelected {
    companion object {
        var ID: Int = -1
        var title: String = ""
        var description: String = ""
        var created_time: Long = 0L
        var created_by_user_ID: Long = 0L
        var assigned_to_user_ID: Long = 0L
        var priority: Int = -1
        var deadline = 0
        var department_ID: Int = 0
        var status: Int = -1
        var progress: Int = -1
    }
}