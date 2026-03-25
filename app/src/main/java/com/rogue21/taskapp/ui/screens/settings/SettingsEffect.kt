package com.rogue21.taskapp.ui.screens.settings

sealed interface SettingsEffect {
    data object NavigateBack : SettingsEffect
}