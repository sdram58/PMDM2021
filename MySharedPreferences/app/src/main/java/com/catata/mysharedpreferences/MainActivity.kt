package com.catata.mysharedpreferences

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.catata.mysharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).also {
            binding = it
        }.root)

        binding.btnLoad.setOnClickListener { loadPreference() }
        binding.btnSave.setOnClickListener { savePreference() }

        loadPreference()
    }

    private fun savePreference() {
        /*val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putString(getString(R.string.key_preference),
                binding.etInfo.text.toString())
            apply()
        }*/

        val sharedPref2 = getSharedPreferences(
            getString(R.string.my_shared_file),
            Context.MODE_PRIVATE
        )
        with(sharedPref2.edit()){
            putString(getString(R.string.key_preference),
                binding.etInfo.text.toString())
            apply()
        }

    }

    private fun loadPreference() {
        //val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val sharedPref2 = getSharedPreferences(
            getString(R.string.my_shared_file),
            Context.MODE_PRIVATE
        )
        val name = sharedPref2.getString(getString(R.string.key_preference), "No saved")
        binding.tvInfo.text = name
    }
}