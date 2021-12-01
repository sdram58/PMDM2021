package com.cartatar.sharedpreferencesexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cartatar.sharedpreferencesexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    val EMPTY_VALUE = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).also { binding = it }.root)

        binding.btnSaveValue.setOnClickListener {
            SharedApp.prefs.name = binding.etName.text.toString ()
            configView()
        }

        binding.btnDeleteValue.setOnClickListener {
            SharedApp.prefs.name = EMPTY_VALUE
            configView()
        }
    }


    fun showProfile() {
        binding.tvName.visibility = View.VISIBLE
        binding.tvName.text = "Hello ${SharedApp.prefs.name}"
        binding.btnDeleteValue.visibility = View.VISIBLE
        binding.etName.visibility = View.INVISIBLE
        binding.btnSaveValue.visibility = View.INVISIBLE
    }

    fun showGuest() {
        binding.tvName.visibility = View.INVISIBLE
        binding.btnDeleteValue.visibility = View.INVISIBLE
        binding.etName.visibility = View.VISIBLE
        binding.btnSaveValue.visibility = View.VISIBLE
    }

    fun configView () {
        if (isSavedName ()) showProfile () else showGuest ()
    }

    fun isSavedName (): Boolean {
        val myName = SharedApp.prefs.name
        return myName != EMPTY_VALUE
    }
}