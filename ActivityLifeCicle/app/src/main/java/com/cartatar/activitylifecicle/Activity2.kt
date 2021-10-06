package com.cartatar.activitylifecicle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cartatar.activitylifecicle.databinding.Activity2Binding

class Activity2 : AppCompatActivity() {
    lateinit var binding: Activity2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = Activity2Binding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}