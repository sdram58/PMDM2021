package com.catata.tasknotesroom.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.catata.tasknotesroom.database.entities.TaskEntity

@Database(entities = arrayOf(TaskEntity::class), version = 1)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}