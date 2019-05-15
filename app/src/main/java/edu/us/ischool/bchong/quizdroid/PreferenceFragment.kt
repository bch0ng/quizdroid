package edu.us.ischool.bchong.quizdroid

import android.app.AlarmManager
import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import org.jetbrains.anko.activityManager
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.concurrent.Executors
import kotlin.concurrent.fixedRateTimer

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        Log.i("BRANDON", "Settings Frag")
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val pref = findPreference<Preference>("fetch_button")
            pref?.setOnPreferenceClickListener {
                val prefs = PreferenceManager.getDefaultSharedPreferences(this.activity)
                val period: Long = prefs.getString("refresh", "").toLong() * 60 * 1000
                val path = Environment.getDataDirectory().absolutePath
                val act = this.activity

                fixedRateTimer("quizdroid", false, 0L, period) {
                    activity?.runOnUiThread {
                        doAsync {
                            if (isOnline()) {
                                Toast.makeText(activity, prefs.getString("url", ""), Toast.LENGTH_SHORT).show()
                                val repo = QuizApp().accessRepo()

                                var exception: Exception? = null
                                var result: String = ""
                                try {
                                    result = URL(prefs.getString("url", "")).readText()
                                    Log.d("Result", result)
                                } catch (e: Exception) {
                                    exception = e
                                }
                                uiThread {
                                    if (exception != null) {
                                        Log.d("EXCEPTION: ", exception.toString())
                                    } else {
                                        File("$path/questions.json").bufferedWriter().use { out -> out.write(result) }
                                        repo.parseJSON()
                                    }
                                }
                            } else {
                                if (Settings.System.getInt(context?.getContentResolver(),
                                        Settings.Global.AIRPLANE_MODE_ON, 0) != 0) {
                                    Log.d("BRANDON", "airplane mode on")
                                    createDialog("Would you like to turn airplane mode off?").show()
                                    //Toast.makeText(SettingsActivity.class, "hello", Toast.LENGTH_SHORT).show()
                                } else {
                                    Log.d("BRANDON", "no internet")
                                    Toast.makeText(activity, "Not connected to the internet.", Toast.LENGTH_SHORT).show()
                                }
                                cancel()
                            }
                        }
                    }
                }
                true
            }
    }

    private fun createDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            Log.d("BRNDOAND", this.context.toString())
            val builder = AlertDialog.Builder(savedInstanceState.)
                builder.setMessage(text)
                .setPositiveButton("Okay",
                    DialogInterface.OnClickListener { dialog, _ ->
                        if (text.contains("airplane")) {
                            startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
                        } else {
                            dialog.cancel()
                        }
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun isOnline(): Boolean {
        val connMgr = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }
}
