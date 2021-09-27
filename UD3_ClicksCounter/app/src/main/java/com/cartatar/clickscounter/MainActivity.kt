package com.cartatar.clickscounter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.cartatar.clickscounter.databinding.ActivityMainBinding
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    var counter = 0

    private val tvClicksCounter:TextView by lazy { findViewById(R.id.clicksCounter) }
    private val btnIncreaseCounter:TextView by lazy { findViewById(R.id.increaseCounter) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btnIncreaseCounter.setOnClickListener(){
            counter++;
            tvClicksCounter.setText("You have clicked $counter times")
        }
    }
}

/********************* con ViewBinding **************
class MainActivity : AppCompatActivity() {

    var counter = 0

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.increaseCounter.setOnClickListener(){
            counter++;
            binding.clicksCounter.setText("You have clicked $counter times")
        }
    }
}
 */