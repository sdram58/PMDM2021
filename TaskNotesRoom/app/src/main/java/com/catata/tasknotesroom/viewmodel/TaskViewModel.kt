package com.catata.tasknotesroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.catata.tasknotesroom.database.TasksDatabase
import com.catata.tasknotesroom.database.entities.TaskEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application) {
    val context = application

    var myDao = Room.databaseBuilder(context, TasksDatabase::class.java, "tasks-db").build().taskDao()

    val taskListLD:MutableLiveData<MutableList<TaskEntity>> = MutableLiveData()

    var tasks = mutableListOf<TaskEntity>()
    init {
        CoroutineScope(Dispatchers.IO).launch {
            tasks = myDao.getAllTasks()
            taskListLD.postValue(tasks)
        }
    }
    fun add(task:String) {
        CoroutineScope(Dispatchers.IO).launch {
            val id = myDao.addTask(TaskEntity(name = task))
            val recoveryTask = myDao.getTaskById(id)
            tasks.add(recoveryTask)
            taskListLD.postValue(tasks)
        }
    }

    fun delete(task:TaskEntity){
        CoroutineScope(Dispatchers.IO).launch {
            val position = tasks.indexOf(task)
            myDao.deleteTask(task)
            tasks.remove(task)

            taskListLD.postValue(tasks)
        }
    }

    fun update(task:TaskEntity){
        CoroutineScope(Dispatchers.IO).launch {
            val pos = tasks.indexOf(task)
            tasks[pos].isDone = !tasks[pos].isDone
            myDao.updateTask(tasks[pos])
            taskListLD.postValue(tasks)
        }
    }
}