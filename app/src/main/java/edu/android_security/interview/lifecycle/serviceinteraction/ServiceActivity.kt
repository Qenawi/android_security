package edu.android_security.interview.lifecycle.serviceinteraction

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import edu.android_security.ui.theme.Android_securityTheme

class ServiceActivity : ComponentActivity() {
    private var ticks: String = ""
    private lateinit var myReceiver: MyServiceResultReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService()
        setContent {
            val state = remember { mutableStateOf(ticks) }
            Android_securityTheme {
                Text(text = "$state")
            }
        }
    }

    private fun startService() {

        val serviceIntent = Intent(this, BackgroundService::class.java)
        startService(serviceIntent)
    }

    fun updateUiFromService(tick: String) {
        ticks = tick
    }

}