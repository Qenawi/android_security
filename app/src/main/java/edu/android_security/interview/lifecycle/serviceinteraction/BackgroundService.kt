package edu.android_security.interview.lifecycle.serviceinteraction

import android.app.Activity
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.annotation.RestrictTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class MyServiceResultReceiver(private val onReceiveCallback: (String) -> Unit) :
    BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val result = intent?.getStringExtra("result")
        result?.let {
            // Use the callback to pass the result to the activity
            onReceiveCallback(it)
        }
    }
}


class BackgroundService : Service() {
    private var serviceJop: Job? = null


    override fun onCreate() {
        super.onCreate()
        serviceJop = Job()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceJop?.let {
            CoroutineScope(Dispatchers.IO + it).launch()
            {
                doSomething()
            }
        }

        // dont restart service if killed
        return START_NOT_STICKY
    }


    suspend fun doSomething() {
        for (i in 0..10) {
            delay(1000)
            val broadcastIntent = Intent("com.example.MY_SERVICE_RESULT")
            broadcastIntent.putExtra("result", "Task Completed")
            sendBroadcast(broadcastIntent)
        }


    }

    // Dont bind
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    // Clear Resources
    override fun onDestroy() {
        super.onDestroy()
        serviceJop?.cancel()
    }
}