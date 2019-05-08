package edu.us.ischool.bchong.quizdroid

import android.os.Environment
import android.util.Log
import java.io.File
import java.lang.Exception
import android.util.JsonReader
import com.google.gson.Gson
import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.FileReader
import java.io.InputStreamReader


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
        if (allTopicsList.isEmpty()) {
            for (topic in topics) {
                allTopicsList.add(topic.value.title)
            }
        }
        return allTopicsList
    }

    init {
        val path = Environment.getDataDirectory().absolutePath
        val file = File("$path/questions.json")
        val jsonArray = JSONArray(file.readText())
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

            val topic = Topic(jsonTopic.get("title").toString(),
                    jsonTopic.get("desc").toString(),
                    jsonTopic.get("desc").toString(),
                    questionList)
            topics.put(jsonTopic.get("title").toString(), topic)
        }
        /*

        val mathTopic = Topic("Math",
            "Math questions focusing on addition, subtraction, division, and multiplication.",
            "Math questions focusing on addition, subtraction, division, and multiplication.",
            listOf(Quiz("2 + 2 = _", listOf("4", "2", "0", "-4"), 0),
                    Quiz("2 + 3 * 5 = _", listOf("-17", "17", "30", "-30"), 1)))

        val physicsTopic = Topic("Physics",
            "Physics questions focusing on mechanics, electromagnetism, and waves+optics.",
            "Physics questions focusing on mechanics, electromagnetism, and waves+optics.",
            listOf(Quiz("Who is credited with the Theory of Relativity?",
                        listOf("Albert Einstein", "Isaac Newton", "Stephen Hawking", "Brandon Chong"),
                0),
                Quiz("Who formulated the Laws of Motion?",
                        listOf("Albert Einstein", "Isaac Newton", "Stephen Hawking", "Brandon Chong"),
                1)))

        val marvelTopic = Topic("Marvel Heroes",
            "Questions about Marvel super heroes from comics and cinema.",
            "Questions about Marvel super heroes from comics and cinema.",
            listOf(Quiz("Who is the man acting as super hero Spider-man in the 2002 film Spider-Man?",
                listOf("Peter Parker", "Tony Stark", "Steve Rogers", "Thanos"),
                0),
                Quiz("What is the super hero name of Tony Stark in the 2008 film Iron Man?",
                    listOf("Captain America", "Thanos", "Iron Man", "Brandon Chong"),
                    2)))

        val electronicsTopic = Topic("Electronics",
            "Questions about Electronical devices.",
            "Questions about Electronical devices.",
            listOf(Quiz("What company creating the XBox?",
                listOf("Microsoft", "Nintendo", "Sony", "SEGA"),
                0),
                Quiz("What is the name of Apple's mobile phones?",
                    listOf("Galaxy", "Pixel", "iPhone", "iPad"),
                    2)))

        this.topics = hashMapOf("math" to mathTopic, "physics" to physicsTopic,
                "marvel" to marvelTopic, "electronics" to electronicsTopic)
         */
    }
}