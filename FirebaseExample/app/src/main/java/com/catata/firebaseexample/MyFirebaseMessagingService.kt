package com.catata.firebaseexample

import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.*

class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        CoroutineScope(Dispatchers.Default).launch {
            sendMessage(remoteMessage.notification?.title?:"No message")
        }


    }

    private suspend fun sendMessage(text:String) = withContext(Dispatchers.Main){
        Toast.makeText(baseContext, text,Toast.LENGTH_SHORT).show()
    }
}