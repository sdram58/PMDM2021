package com.catata.directoriesfilesshareddao

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.catata.directoriesfilesshareddao.dao.DaoFiles
import com.catata.directoriesfilesshareddao.dao.DaoShared
import com.catata.directoriesfilesshareddao.dao.IMyDAO
import com.catata.directoriesfilesshareddao.databinding.ActivityMainBinding
import com.catata.directoriesfilesshareddao.model.People
import com.catata.directoriesfilesshareddao.videmodel.DaoViewModel
import java.io.*




class MainActivity : AppCompatActivity() {

    private lateinit var viewModelDao:DaoViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).also { binding = it }.root)

        viewModelDao = ViewModelProvider(this)[DaoViewModel::class.java]

        binding.btnLoad.setOnClickListener {
            viewModelDao.load()
        }

        binding.btnSave.setOnClickListener {
            viewModelDao.save(
                People(
                    binding.etName.text.toString(),
                    binding.etSurname.text.toString()
                )
            )
        }



        binding.swDAO.setOnCheckedChangeListener { switchDAO, isChecked ->
            viewModelDao.changeDao(isChecked)

        }

        viewModelDao.nameLD.observe(this){ people ->
            binding.etName.setText(people.name)
            binding.etSurname.setText(people.surname)
        }

        viewModelDao.errorLD.observe(this){ error ->
            Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
        }

        viewModelDao.switchText.observe(this){ text ->
            binding.swDAO.text = text
        }

    }



}