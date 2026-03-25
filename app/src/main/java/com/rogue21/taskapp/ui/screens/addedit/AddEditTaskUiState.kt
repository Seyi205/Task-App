package com.rogue21.taskapp.ui.screens.addedit

import com.rogue21.taskapp.model.Task

data class AddEditTaskUiState(
    val taskId: Int? = null,
    val taskTitle: String = "",
    val description: String = "",
    val isCompleted: Boolean = false,
    val originalTask: Task? = null,
    val isSaveEnabled: Boolean = false,
    val isSaving: Boolean = false
)