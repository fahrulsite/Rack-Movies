package com.fahrul.rackmovies.ui.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.fahrul.rackmovies.R
import com.fahrul.rackmovies.receiver.AlarmReceiver
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*

class SettingActivity : AppCompatActivity() {
    private val alarmReceiver = AlarmReceiver()
    private val calendar = Calendar.getInstance()
    private lateinit var settingPreference: SharedPreferences

    companion object {
        const val SETTING_PREF = "setting_pref"
        const val DAILY_REMINDER = "daily_reminder"
        const val RELEASE_REMINDER = "release_reminder"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val actionBar = supportActionBar

        settingPreference = getSharedPreferences(SETTING_PREF, Context.MODE_PRIVATE)
        actionBar?.let {
            it.title = getString(R.string.Setting)
            it.setDisplayHomeAsUpEnabled(true)
        }
        setSwitchListener()
    }

    private fun setSwitchListener() {
        switchDailyReminder.isChecked = settingPreference.getBoolean(DAILY_REMINDER, false)
        switchReleaseReminder.isChecked = settingPreference.getBoolean(RELEASE_REMINDER, false)

        switchDailyReminder.setOnCheckedChangeListener { _, isChecked ->
            val editor = settingPreference.edit()

            if (isChecked) {
                // at 7 AM
                calendar.set(Calendar.HOUR_OF_DAY, 7)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)

                alarmReceiver.setAlarm(this, calendar.timeInMillis, AlarmReceiver.ALARM_ID_REMINDER)
                editor.putBoolean(DAILY_REMINDER, true)
            } else {
                alarmReceiver.cancelAlarm(this, AlarmReceiver.ALARM_ID_REMINDER)
                editor.putBoolean(DAILY_REMINDER, false)
            }

            editor.apply()
        }

        switchReleaseReminder.setOnCheckedChangeListener { _, isChecked ->
            val editor = settingPreference.edit()

            if (isChecked) {
                // at 8 AM
                calendar.set(Calendar.HOUR_OF_DAY, 8)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)

                alarmReceiver.setAlarm(this, calendar.timeInMillis, AlarmReceiver.ALARM_ID_RELEASE)
                editor.putBoolean(RELEASE_REMINDER, true)
            } else {
                alarmReceiver.cancelAlarm(this, AlarmReceiver.ALARM_ID_RELEASE)
                editor.putBoolean(RELEASE_REMINDER, false)
            }

            editor.apply()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
