package com.bayuirfan.madesubmission.features.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.services.SchedulerReceiver
import com.bayuirfan.madesubmission.services.SchedulerReceiver.Companion.ID_DAILY
import com.bayuirfan.madesubmission.services.SchedulerReceiver.Companion.ID_RELEASE
import com.bayuirfan.madesubmission.utils.Constant.DAILY_REMINDER
import com.bayuirfan.madesubmission.utils.Constant.PREFERENCES
import com.bayuirfan.madesubmission.utils.Constant.RELEASE_REMINDER
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var schedulerReceiver: SchedulerReceiver
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        schedulerReceiver = SchedulerReceiver()

        btn_language.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        preferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val isDailyChecked = preferences.getBoolean(DAILY_REMINDER, false)
        val isReleaseChecked = preferences.getBoolean(RELEASE_REMINDER, false)

        sw_daily.isChecked = isDailyChecked
        sw_daily.setOnCheckedChangeListener { _, _ ->
            if (isDailyChecked){
                schedulerReceiver.cancelReminder(this, ID_DAILY)
            } else {
                schedulerReceiver.setReminder(this, ID_DAILY)
            }

            preferences.edit()
                    .putBoolean(DAILY_REMINDER, !isDailyChecked)
                    .apply()
        }

        sw_new_release.isChecked = isReleaseChecked
        sw_new_release.setOnCheckedChangeListener { _, _ ->
            if (isReleaseChecked){
                schedulerReceiver.cancelReminder(this, ID_RELEASE)
            } else {
                schedulerReceiver.setReminder(this, ID_RELEASE)
            }

            preferences.edit()
                    .putBoolean(RELEASE_REMINDER, !isReleaseChecked)
                    .apply()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
}