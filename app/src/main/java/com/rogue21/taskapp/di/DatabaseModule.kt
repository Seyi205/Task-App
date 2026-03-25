package com.rogue21.taskapp.di

import android.content.Context
import androidx.room.Room
import com.rogue21.taskapp.data.local.TaskDao
import com.rogue21.taskapp.data.local.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(
        @ApplicationContext context: Context
    ): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task_database"
        )
            .fallbackToDestructiveMigration() // "If schema changes → delete database → recreate it"
            .build()
    }

    @Provides
    fun provideTaskDao(
        database: TaskDatabase
    ): TaskDao {
        return database.taskDao()
    }
}