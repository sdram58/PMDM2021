package com.catata.notificationsexample


import android.app.*
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.catata.notificationsexample.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val pendingIntent: PendingIntent by lazy { makePendingIntent() }
    private val pendingIntentYes: PendingIntent by lazy { makePendingIntentYes() }
    private val pendingIntentNo: PendingIntent by lazy { makePendingIntentNo() }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnSendNotification.setOnClickListener{
            sendNotification()
        }
    }

    private fun sendNotification() {
        makeNotificationChannel()
        makeNotificaton()
    }




    private fun makeNotificationChannel() {
        //Notifications channels are just available from Oreo version and above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Features of channel
            val notificationChannel = NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT)

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            //Notification is visible on the App's icon
            notificationChannel.setShowBadge(true)

            //Creates the notification
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun makeNotificaton() {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, CHANNEL_ID)


        with(builder){
            setSmallIcon(R.drawable.ic_baseline_access_alarm)
            setContentTitle("Notification Example ${Math.random()}")
            setContentText("This is my notification")
            setStyle(NotificationCompat.BigTextStyle()
                .bigText("This is a long text that can't it into a single line. This is a long text that can't it into a single line. This is a long text that can't it into a single line. This is a long text that can't it into a single line"))


            color = Color.BLUE
            priority = NotificationCompat.PRIORITY_MAX

            //1 sec on 1 sec off
            setLights(Color.MAGENTA, 1000, 1000)

            //1 sec on 1 sec off 1 sec on 1 sec off
            setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            //setSound()
            setDefaults(Notification.DEFAULT_SOUND)
            setContentIntent(pendingIntent)

            setVisibility(VISIBILITY_PUBLIC)

            addAction(R.drawable.ic_yes, "Yes", pendingIntentYes)
            addAction(R.drawable.ic_no, "No", pendingIntentNo)


            //setFullScreenIntent(pendingIntent, true)

            //setTimeoutAfter(5000L) //cancels notification after 5 sec
            //setAutoCancel(true)
        }

        val notificationManagerCompat = NotificationManagerCompat.from(
            this)

        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
    }

    private fun makePendingIntent():PendingIntent {
        val intent = Intent(this, NotificationActivity::class.java)

        return PendingIntent.getActivity(this, PENDING_REQUEST, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

    private fun makePendingIntentYes():PendingIntent {
        //That is the real intent
        val intent = Intent(this, YesActivity::class.java).apply {
            putExtra(YES_EXTRA_KEY, "This text goes to Yes Activity")
        }


        //This makes the stack builder with the parent activity (optional)
        //If we don't set it when we press back button the app will exit
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(YesActivity::class.java)
        stackBuilder.addNextIntent(intent)

        return stackBuilder.getPendingIntent(PENDING_REQUEST,
            PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun makePendingIntentNo():PendingIntent {
        //This activity is only accessed by notifications, and what we want later is to close the app
        //So system creates a new task just for this activity instead of adding it to the stack
        val intent = Intent(this, NoActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }


        return PendingIntent.getActivity(this, 0, intent, 0)
    }


    private suspend fun sendMessage(text:String) = withContext(Dispatchers.Main){
        Toast.makeText(this@MainActivity,text, Toast.LENGTH_SHORT).show()
    }





}