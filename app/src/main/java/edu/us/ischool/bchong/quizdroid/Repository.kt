package edu.us.ischool.bchong.quizdroid

import android.os.Environment
import android.util.Log
import java.io.File
import java.lang.Exception
import android.util.JsonReader
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.FileReader
import java.io.InputStreamReader
import java.net.URL
import java.util.concurrent.Executors
import android.os.StrictMode



class Repository : TopicRepositoryInterface {
    private var topics: HashMap<String, Topic> = hashMapOf()
    private var allTopicsList: ArrayList<String> = ArrayList()

    override fun getTopic(topicKey: String): Topic {
        if (!this.topics.contains(topicKey)) {
            throw Exception("Key not found in map.")
        }
        return this.topics.getValue(topicKey)
    }

    override fun getAllTopics(): List<String> {
        return topics.keys.toList()
    }

    init {
        parseJSON()
    }

    fun parseJSON() {
        try {
            val path = Environment.getDataDirectory().absolutePath
            val file = File("$path/questions.json")
            val json = file.readText()
            this.topics.clear()

            val jsonArray = JSONArray(json)
            val gson = Gson()
            for (i in 0..(jsonArray.length() - 1)) {
                val jsonTopic = jsonArray.getJSONObject(i)

                val questionList = ArrayList<Quiz>()
                val question = jsonTopic.getJSONArray("questions")
                for (i in 0..(question.length() - 1)) {
                    val question = question.getJSONObject(i)
                    val questionText = question.get("text").toString()
                    val answer = question.get("answer")

                    val answers: List<String> = listOf(
                        question.getJSONArray("answers").get(0).toString(),
                        question.getJSONArray("answers").get(1).toString(),
                        question.getJSONArray("answers").get(2).toString(),
                        question.getJSONArray("answers").get(3).toString()
                    )

                    questionList.add(Quiz(questionText, answers, answer.toString().toInt()))
                }

                val topic = Topic(
                    jsonTopic.get("title").toString(),
                    jsonTopic.get("desc").toString(),
                    jsonTopic.get("desc").toString(),
                    questionList
                )
                this.topics.put(jsonTopic.get("title").toString(), topic)
            }
        } catch (error: Exception) {
            Log.d("EXCEPTION: ", error.toString())
        }
    }
}