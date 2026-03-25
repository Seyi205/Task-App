package com.rogue21.taskapp.ui.screens.addedit

sealed interface AddEditTaskEffect {
    data object NavigateBack : AddEditTaskEffect
}