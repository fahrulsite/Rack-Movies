package com.fahrul.rackmovies.ui.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.fahrul.rackmovies.R
import com.fahrul.rackmovies.notif.AlarmReceiver
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*

class SettingActivity : AppCompatActivity() {
    private val alarmReceiver = AlarmReceiver()
    private val calendar = Calendar.getInstance()
    private lateinit var settingPreference: SharedPreferences

    companion object {
        const val SETTING_PREF = "setting_pref"
        const val setReminder = "daily_reminder"
        const val setRelease = "release_reminder"
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
        swReminder.isChecked = settingPreference.getBoolean(setReminder, false)
        swRelease.isChecked = settingPreference.getBoolean(setRelease, false)

        swReminder.setOnCheckedChangeListener { _, isChecked ->
            val editor = settingPreference.edit()

            if (isChecked) {
                // at 7 AM
                calendar.set(Calendar.HOUR_OF_DAY, 7)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)

                alarmReceiver.setAlarm(this, calendar.timeInMillis, AlarmReceiver.idReminder)
                editor.putBoolean(setReminder, true)
            } else {
                alarmReceiver.cancelAlarm(this, AlarmReceiver.idReminder)
                editor.putBoolean(setReminder, false)
            }

            editor.apply()
        }

        swRelease.setOnCheckedChangeListener { _, isChecked ->
            val editor = settingPreference.edit()

            if (isChecked) {
                // at 8 AM
                calendar.set(Calendar.HOUR_OF_DAY, 8)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)

                alarmReceiver.setAlarm(this, calendar.timeInMillis, AlarmReceiver.idRelease)
                editor.putBoolean(setRelease, true)
            } else {
                alarmReceiver.cancelAlarm(this, AlarmReceiver.idRelease)
                editor.putBoolean(setRelease, false)
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
