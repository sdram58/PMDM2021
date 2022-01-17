package com.catata.notificationsexample

import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationManagerCompat

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        title = "Notification activity"

        NotificationManagerCompat.from(this).apply {
            cancel(NOTIFICATION_ID)
        }
    }
}