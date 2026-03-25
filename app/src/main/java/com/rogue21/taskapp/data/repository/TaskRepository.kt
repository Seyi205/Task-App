package com.rogue21.taskapp.data.repository

import com.rogue21.taskapp.data.local.TaskDao
import com.rogue21.taskapp.data.mapper.toEntity
import com.rogue21.taskapp.data.mapper.toTask
import com.rogue21.taskapp.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
){
    fun getTasks(): Flow<List<Task>> {
        // DAO returns Flow<List<TaskEntity>> because Room works with entities.
        return taskDao.getTasks()
            .map { entities ->
                entities.map {it.toTask()}
                // 'it' in this context is basically shorthand for "entity"
                // Direction here: Database/DAO -> Repository -> ViewModel/UI
                // So this conversion is: TaskEntity -> Task
            }
    }

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task.toEntity())
        // ViewModel gives repository a Task, not a TaskEntity.
        // Direction here: ViewModel/UI -> Repository -> DAO/Database
        // So this conversion is: Task -> TaskEntity
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toEntity())
        // Same idea as insert, Direction is ViewModel/UI -> Repository -> DAO/Database.
    }

    suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
        // deleteAllTasks() does NOT any need conversion unlike deleteTask()
        // We are not deleting a specific object, we are issuing a command.
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())
    }

    suspend fun getTaskById(id: Int): Task? {
        return taskDao.getTaskById(id)?.toTask()
    }
}