package com.rogue21.taskapp.model

data class Task(
    val id: Int = 0,
    val title: String,
    //A title is required upon object instantiation
    // prevents you from accidentally creating "empty" tasks.
    val description: String = "",
    val isCompleted: Boolean = false
)