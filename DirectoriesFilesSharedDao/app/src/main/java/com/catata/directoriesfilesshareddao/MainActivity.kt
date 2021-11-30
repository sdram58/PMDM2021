package com.catata.directoriesfilesshareddao

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.catata.directoriesfilesshareddao.dao.DaoFiles
import com.catata.directoriesfilesshareddao.dao.DaoShared
import com.catata.directoriesfilesshareddao.dao.IMyDAO
import com.catata.directoriesfilesshareddao.databinding.ActivityMainBinding
import com.catata.directoriesfilesshareddao.model.People
import java.io.*




class MainActivity : AppCompatActivity() {

    lateinit var myDAO: IMyDAO

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).also { binding = it }.root)

        binding.btnLoad.setOnClickListener {
            load()
        }

        binding.btnSave.setOnClickListener {
            save()
        }

        myDAO = DaoFiles(this)

        binding.swDAO.setOnCheckedChangeListener { switchDAO, isChecked ->
            if(isChecked)
            {
                switchDAO.text = "Shared"
                myDAO = DaoShared.getInstance(this)
            }
            else{
                switchDAO.text = "File"
                myDAO = DaoFiles.getInstance(this)
            }

        }

    }



    private fun save() {

       myDAO.save(
           People(
               binding.etName.text.toString(),
               binding.etSurname.text.toString()
           )
       )
    }

    private fun load() {
       val people = myDAO.load()
        binding.etName.setText(people.name)
        binding.etSurname.setText(people.surname)
    }






}