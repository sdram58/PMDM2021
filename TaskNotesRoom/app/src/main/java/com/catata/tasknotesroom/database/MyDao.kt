package com.catata.tasknotesroom.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.catata.tasknotesroom.database.entities.TaskEntity

interface MyDao {
    fun getAllTasks(): MutableList<TaskEntity>

    fun addTask(taskEntity : TaskEntity):Long

    fun getTaskById(id: Long): TaskEntity

    fun updateTask(taskEntity: TaskEntity):Int

    fun deleteTask(taskEntity: TaskEntity):Int
}