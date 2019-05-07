package edu.us.ischool.bchong.quizdroid

import android.util.Log
import android.arch.lifecycle.LiveData


class QuizApp : android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("QuizApp", "Loaded and running.")
    }

    companion object {
        val instance = QuizApp()
    }
}