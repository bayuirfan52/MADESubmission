package com.bayuirfan.madesubmission.services

import android.content.*

class SchedulerReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        
    }

    companion object {
        const val DAILY_REPEATING = "DAILY_REPEATING"
        const val NEW_RELEASE = "NEW_RELEASE"
        const val ID_DAILY = 101
        const val ID_REPEATING = 102
    }
}