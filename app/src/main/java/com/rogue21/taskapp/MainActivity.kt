package com.rogue21.taskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.rogue21.taskapp.data.local.TaskDatabase
import com.rogue21.taskapp.data.repository.TaskRepository
import com.rogue21.taskapp.ui.navigation.NavGraph
import com.rogue21.taskapp.ui.theme.TaskAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java,
            "task_database"
        ).build()
    }

    private val repository by lazy {
        TaskRepository(database.taskDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskAppTheme {
                NavGraph(repository = repository)
            }
        }
    }
}