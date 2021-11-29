package com.catata.savestateexamen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf

const val TAG = "SAVE_STATE"
const val BUNDLE_KEY = "BUNDLE_KEY"
class MainActivity : AppCompatActivity() {

    private val tvCounter: TextView by lazy{ findViewById(R.id.tvCounter)}
    private val btnInc: Button by lazy{ findViewById(R.id.btnInc)}

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        log("We are in onCreate")

        btnInc.setOnClickListener {
            incCounter()
        }

    }

    private fun incCounter() {
        counter++

        updateTvCounter()
    }

    private fun updateTvCounter() {
        tvCounter.text = counter.toString()
    }

    private fun log(text:String){
        Log.d(TAG, text)
    }

    override fun onResume() {
        super.onResume()
        log("We are in onResume")
        updateTvCounter()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        log("We are in onSaveInstanceState")

        outState.putInt(BUNDLE_KEY,counter)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        counter = savedInstanceState.getInt(BUNDLE_KEY)
        log("We are in onRestoreInstanceState")
    }

}