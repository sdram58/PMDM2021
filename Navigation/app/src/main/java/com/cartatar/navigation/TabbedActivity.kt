package com.cartatar.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TabbedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed)

        title = "Tabbed Activity"
    }
}