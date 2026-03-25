package com.rogue21.taskapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object TaskListRoute

@Serializable
object SettingsRoute

@Serializable
data class AddEditTaskRoute(
    val taskId: Int? = null
)