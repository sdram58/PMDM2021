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
    val updateTaskLD:MutableLiveData<TaskEntity?> = MutableLiveData()
    val deleteTaskLD:MutableLiveData<Int> = MutableLiveData()
    val insertTaskLD:MutableLiveData<TaskEntity> = MutableLiveData()

    lateinit var tasks :MutableList<TaskEntity>


    fun getAllTasks(){
        CoroutineScope(Dispatchers.IO).launch {
            tasks = myDao.getAllTasks()
            taskListLD.postValue(tasks)
        }

    }
    fun add(task:String) {
        CoroutineScope(Dispatchers.IO).launch {
            val id = myDao.addTask(TaskEntity(name = task))
            val recoveryTask = myDao.getTaskById(id)
            insertTaskLD.postValue(recoveryTask)
        }
    }

    fun delete(task:TaskEntity){
        CoroutineScope(Dispatchers.IO).launch {

            val res = myDao.deleteTask(task)
            if(res>0)
                deleteTaskLD.postValue(task.id)
            else{
                deleteTaskLD.postValue(-1)
            }


        }
    }

    fun update(task:TaskEntity){
        CoroutineScope(Dispatchers.IO).launch {
            task.isDone = !task.isDone
            val res = myDao.updateTask(task)
            if(res>0)
                updateTaskLD.postValue(task)
            else
                updateTaskLD.postValue(null)
        }
    }
}