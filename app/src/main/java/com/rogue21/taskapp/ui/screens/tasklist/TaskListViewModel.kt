package com.rogue21.taskapp.ui.screens.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rogue21.taskapp.data.repository.TaskRepository
import com.rogue21.taskapp.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    val tasks: StateFlow<List<Task>> = repository.getTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun addTask(title: String) {
        viewModelScope.launch {
            repository.insertTask(Task(title = title))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun setTaskCompleted(task: Task, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateTask(
                task.copy(isCompleted = isCompleted)
            )
        }
    }
}