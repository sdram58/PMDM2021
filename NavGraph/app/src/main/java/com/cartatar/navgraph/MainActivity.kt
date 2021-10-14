package com.cartatar.navgraph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity(),ChangeToolbar {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }


    override fun changeToolbar(change: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}

interface ChangeToolbar{


    fun changeToolbar(change:Boolean)
}