package com.catata.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import java.lang.StringBuilder


class MyBroadcastReceiver:BroadcastReceiver() {
    private val TAG = "MyBroadcastReceiver"
    private lateinit var context:Context

    override fun onReceive(ctx: Context?, intent: Intent?) {
        context = ctx!!
        when(intent?.action){
            MY_ACTION_RECEIVER_ACTION -> {
                val sb = StringBuilder()
                sb.append("""
                    Action: ${intent.action}
                 """.trimIndent())
                sb.append("""
                    URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}
                 """.trimIndent())
                val log = sb.toString()
                Log.d(TAG, log)

                sendMessage("Users action is received with value ${intent.getStringExtra(MY_ACTION_RECEIVER_EXTRA)}")
            }
            ACTION_POWER_CONNECTED ->{
                sendMessage("Power Connected")
            }
            ACTION_POWER_DISCONNECTED ->{
                sendMessage("Power disconnected")
            }

        }
    }

    private fun sendMessage(s: String) {
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show()
    }

    companion object{
        val MY_ACTION_RECEIVER_ACTION = MyBroadcastReceiver::class.java.canonicalName + ".ACTION_RECEIVER"
        val MY_ACTION_RECEIVER_EXTRA = MyBroadcastReceiver::class.java.canonicalName + ".RECEIVER_EXTRA"
        const val ACTION_POWER_CONNECTED = "android.intent.action.ACTION_POWER_CONNECTED"
        const val ACTION_POWER_DISCONNECTED = "android.intent.action.ACTION_POWER_DISCONNECTED"


    }


}