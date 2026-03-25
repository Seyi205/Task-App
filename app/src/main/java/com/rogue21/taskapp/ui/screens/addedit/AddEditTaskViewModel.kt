package com.rogue21.taskapp.ui.screens.addedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rogue21.taskapp.data.repository.TaskRepository
import com.rogue21.taskapp.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEditTaskUiState())
    val uiState: StateFlow<AddEditTaskUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<AddEditTaskEffect>()
    val effects: SharedFlow<AddEditTaskEffect> = _effects.asSharedFlow()

    private fun computeIsSaveEnabled(state: AddEditTaskUiState): Boolean {
        if (state.taskTitle.isBlank()) return false

        val original = state.originalTask

        // If creating → just check title
        if (original == null) return true

        // If editing → check if anything changed
        return state.taskTitle != original.title ||
                state.description != original.description ||
                state.isCompleted != original.isCompleted
    }

    private fun updateState(transform: (AddEditTaskUiState) -> AddEditTaskUiState) {
        _uiState.update { current ->
            val updated = transform(current)
            updated.copy(isSaveEnabled = computeIsSaveEnabled(updated))
        }
    }


    fun onCompletedChange(isCompleted: Boolean) {
        updateState { it.copy(isCompleted = isCompleted) }
    }

    fun onTitleChange(newTitle: String) {
        updateState { it.copy(taskTitle = newTitle) }
    }

    fun onDescriptionChange(newDescription: String) {
        updateState { it.copy(description = newDescription) }
    }

    fun saveTask() {
        val state = _uiState.value
        val title = state.taskTitle.trim()

        if (title.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            val task = Task(
                id = state.taskId ?: 0,
                title = title,
                description = state.description,
                isCompleted = state.isCompleted
            )

            if (state.taskId == null) {
                // 🟢 CREATE
                repository.insertTask(task)
            } else {
                // 🔵 UPDATE
                repository.updateTask(task)
            }

            _uiState.update { it.copy(isSaving = false) }
            _effects.emit(AddEditTaskEffect.NavigateBack)
        }
    }

    fun loadTask(taskId: Int) {
        viewModelScope.launch {
            val task = repository.getTaskById(taskId) ?: return@launch

            _uiState.update {
                it.copy(
                    taskId = task.id,
                    taskTitle = task.title,
                    description = task.description,
                    isCompleted = task.isCompleted,
                    originalTask = task
                )
            }
        }
    }

//    fun onCompletedChange(isCompleted: Boolean) {
//        _uiState.update {
//            val updated = it.copy(isCompleted = isCompleted)
//
//            updated.copy(
//                isSaveEnabled = computeIsSaveEnabled(updated)
//            )
//        }
//    }

}