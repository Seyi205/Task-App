package com.rogue21.taskapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY id ASC")
    fun getTasks(): Flow<List<TaskEntity>>
    // Returns a live stream of all tasks ordered by ID and any time the database changes,
    // your UI automatically gets the updated list without you having to re-query manually
    // Kotlin handles the async nature of flows differently, so suspend isn't needed

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)
    // We are insertng a new object into the tasks table, and we need TaskEntity(id = ?, title = ?, etc)
    // The OnConflictStrategy.REPLACE means if a task with the same ID already exists,
    // it overwrites it rather than crashing.

    @Delete
    suspend fun deleteTask(task: TaskEntity)
    // We are deleting a specific object from the tasks table.
    // Room uses the entity's primary key (id) under the hood to know which row to remove.

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
    // It just clears the table.

    @Update
    suspend fun updateTask(task: TaskEntity)
    // We are updating a specific object from the tasks table.
    // Room uses the entity's primary key (id) under the hood to know which row to update.

    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    suspend fun getTaskById(id: Int): TaskEntity?
    // Fetches a single task by its ID. LIMIT 1 makes it efficient since it stops scanning once found.
    // Returns TaskEntity? (nullable) in case no match exists.
    // Database does the filtering, so its faster and avoids loading full list
}