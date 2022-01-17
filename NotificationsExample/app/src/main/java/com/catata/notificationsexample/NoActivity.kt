package com.catata.notificationsexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no)
        title = "No activity"
    }
}