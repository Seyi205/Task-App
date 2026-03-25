package com.rogue21.taskapp.ui.screens.settings

data class SettingsUiState(
    val appName: String = "Task App",
    val appDescription: String = "A simple task management app, built with Jetpack Compose, Room, and Hilt.",
    val isDeleteAllEnabled: Boolean = true
)