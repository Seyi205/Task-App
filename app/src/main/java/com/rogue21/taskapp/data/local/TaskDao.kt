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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)
    // We are insertng a new object into the tasks table, and we need TaskEntity(id = ?, title = ?)

    @Delete
    suspend fun deleteTask(task: TaskEntity)
    // We are deleting a specific object from the tasks table

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
    // It just clears the table.

    @Update
    suspend fun updateTask(task: TaskEntity)
    // We are updating a specific object from the tasks table.

    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    suspend fun getTaskById(id: Int): TaskEntity?
    // Database does the filtering, so its faster and avoids loading full list

}