package com.rogue21.taskapp.data.mapper

import com.rogue21.taskapp.data.local.TaskEntity
import com.rogue21.taskapp.model.Task

fun TaskEntity.toTask(): Task =
    Task(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted
    )

fun Task.toEntity(): TaskEntity =
    TaskEntity(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted
    )