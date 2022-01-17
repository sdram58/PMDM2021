package com.catata.notificationsexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationManagerCompat

class NoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no)
        title = "No activity"

        NotificationManagerCompat.from(this).apply {
            cancel(NOTIFICATION_ID)
        }
    }
}