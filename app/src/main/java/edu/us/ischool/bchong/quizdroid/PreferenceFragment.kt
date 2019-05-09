package edu.us.ischool.bchong.quizdroid

import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        Log.i("BRANDON", "Settings Frag")
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
