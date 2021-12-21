package com.catata.tasknotesroom.database

import androidx.room.*
import com.catata.tasknotesroom.database.entities.TaskEntity

@Dao
interface TaskDao:MyDao {
    @Query("SELECT * FROM task_entity")
    override fun getAllTasks(): MutableList<TaskEntity>

    @Insert
    override fun addTask(taskEntity : TaskEntity):Long

    @Query("SELECT * FROM task_entity WHERE id LIKE :id")
    override fun getTaskById(id: Long): TaskEntity

    @Update
    override fun updateTask(taskEntity: TaskEntity):Int //Return Value is Number of affected row.

    @Delete
    override fun deleteTask(taskEntity: TaskEntity):Int
}