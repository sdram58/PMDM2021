package com.cartatar.navgraph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(),ChangeToolbar {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun changeToolbar(change: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}

interface ChangeToolbar{
    fun changeToolbar(change:Boolean)
}