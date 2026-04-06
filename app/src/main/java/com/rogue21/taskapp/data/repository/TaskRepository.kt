package com.rogue21.taskapp.data.repository

import com.rogue21.taskapp.data.local.TaskDao
import com.rogue21.taskapp.data.mapper.toEntity
import com.rogue21.taskapp.data.mapper.toTask
import com.rogue21.taskapp.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
){
    fun getTasks(): Flow<List<Task>> {
        // DAO returns Flow<List<TaskEntity>> because Room works with entities.
        return taskDao.getTasks()
            .map { entities -> // map on the FLOW
                entities.map { // map on the LIST inside
                    it.toTask() // the transform: TaskEntity → Task
                }
            }
/**
                We have two maps because the data structure is nested: Flow<List<TaskEntity>>.
                Each map handles exactly one layer.
                One map to unwrap the Flow layer, and another to iterate the List layer inside it.
                Outer .map — this is Flow.map. It says "whenever a new list arrives through the flow,
                run this block on it." The entities parameter is that List<TaskEntity>.
                Inner .map — this is List.map. It says "for every TaskEntity in this list, convert it."
                The 'it' is each individual TaskEntity.
*/

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

// This is what Hilt resolves automatically when something needs a TaskRepository
/**
 * TaskListViewModel
 *     └── @Inject constructor(repository: TaskRepository)
 *             └── @Inject constructor(taskDao: TaskDao)
 *                     └── @Provides provideTaskDao(database)
 *                             └── @Provides @Singleton provideTaskDatabase(context)
 *                                     └── @ApplicationContext (Hilt built-in)
 */