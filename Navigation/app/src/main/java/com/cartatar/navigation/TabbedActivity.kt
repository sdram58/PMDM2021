package com.cartatar.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class TabbedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed)

        val toolBar:Toolbar = findViewById(R.id.toolbar)
        toolBar.title = "Tabbed Activity"
        setSupportActionBar(toolBar)

    }
}