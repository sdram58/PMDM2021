package com.catata.tasknotesroom.ui

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catata.tasknotesroom.adapters.TasksAdapter
import com.catata.tasknotesroom.database.entities.TaskEntity
import com.catata.tasknotesroom.databinding.ActivityMainBinding
import com.catata.tasknotesroom.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var recyclerView: RecyclerView
    var tasks: MutableList<TaskEntity> = mutableListOf()

    private lateinit var taskViewModel:TaskViewModel

    lateinit var adapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).also {
            binding = it
        }.root)



        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        taskViewModel.taskListLD.observe(this){
            tasks.clear()
            tasks.addAll(it)
            recyclerView.adapter?.notifyDataSetChanged()
        }

        binding.btnAddTask.setOnClickListener {
            addTask()
        }

        setUpRecyclerView()
    }


    private fun addTask() {
        taskViewModel.add(binding.etTask.text.toString())
        clearFocus()
        hideKeyboard()

    }




    fun setUpRecyclerView() {
        adapter = TasksAdapter(tasks, { taskEntity ->  updateTask(taskEntity) }, { taskEntity -> deleteTask(taskEntity) })
        recyclerView = binding.rvTask
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun updateTask(taskEntity: TaskEntity) {
       taskViewModel.update(taskEntity)
    }

    private fun deleteTask(taskEntity: TaskEntity) {
        taskViewModel.delete(taskEntity)

    }

    fun clearFocus(){
        binding.etTask.setText("")
    }

    fun Context.hideKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}