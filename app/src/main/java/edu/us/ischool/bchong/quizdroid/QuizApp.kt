package edu.us.ischool.bchong.quizdroid

import android.util.Log
import android.arch.lifecycle.LiveData


class QuizApp : android.app.Application() {
    private val repo: Repository = Repository()

    override fun onCreate() {
        super.onCreate()
        Log.d("QuizApp", "Loaded and running.")
    }

    fun accessRepo(): Repository {
        return this.repo
    }

    companion object {
        private val instance: QuizApp = QuizApp()

        fun getInstance(): QuizApp {
            return instance
        }
    }
}