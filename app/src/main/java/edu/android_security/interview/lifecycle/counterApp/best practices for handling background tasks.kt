package edu.android_security.interview.lifecycle.counterApp

import android.os.Bundle
import androidx.activity.ComponentActivity

class BestPracticesForHandlingBackgroundTasks : ComponentActivity() {

    // create  life cycle events
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Light Weight Initiation Tasks
    }

    override fun onStart() {
        super.onStart()
        // start background tasks or network requests here
        // view is fully Visible
    }

    override fun onResume() {
        super.onResume()
        // resume any tasks that were paused
        // immediate display
    }

    override fun onPause() {
        super.onPause()
        // pause or cancel any tasks that should not be running in the background while app is paused
    }

    override fun onStop() {
        super.onStop()
        // release resources or stop any tasks that should not be running in the background to avoid leaks
    }

    override fun onDestroy() {
        super.onDestroy()
        // Final clean up task before destroying the View


    }
}