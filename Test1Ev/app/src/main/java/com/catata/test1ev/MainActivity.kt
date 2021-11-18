package com.catata.test1ev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.catata.test1ev.databinding.ActivityMainBinding
import com.catata.test1ev.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginViewModel= ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.loadData()

        setTheme(R.style.Theme_Test1Ev)

        setContentView(
            ActivityMainBinding.inflate(layoutInflater).also{
            binding = it
        }.root)

        setSupportActionBar(binding.toolbar)

        binding.toolbar.title = getString(R.string.first_ev_test)




    }
}