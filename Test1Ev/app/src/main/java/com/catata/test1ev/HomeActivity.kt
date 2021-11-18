package com.catata.test1ev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.catata.test1ev.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private lateinit var appBarConfiguration:AppBarConfiguration
    companion object{
        const val ID_PARAM:String ="ID_PARAM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityHomeBinding.inflate(layoutInflater).also {
            binding = it
        }.root)


        setSupportActionBar(binding.toolbar)

        binding.toolbar.title = "Home"

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val navHosFragment = supportFragmentManager.findFragmentById(R.id.m_nav_host_fragment) as NavHostFragment
        val navController = navHosFragment.navController




        appBarConfiguration = AppBarConfiguration.Builder(navController.graph)
            .setOpenableLayout(drawerLayout)
            .build()
        appBarConfiguration.topLevelDestinations.add(R.id.infoFragment)
        appBarConfiguration.topLevelDestinations.add(R.id.logoutFragment)


        NavigationUI.setupWithNavController(navView, navController)
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
    }
}