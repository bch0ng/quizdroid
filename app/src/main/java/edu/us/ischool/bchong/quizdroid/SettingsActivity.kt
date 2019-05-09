package edu.us.ischool.bchong.quizdroid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        Log.i("BRANDON", "Settings Activity")
        supportFragmentManager
            .beginTransaction()
            .add(R.id.settings_container, SettingsFragment())
            .commit()
    }

}
