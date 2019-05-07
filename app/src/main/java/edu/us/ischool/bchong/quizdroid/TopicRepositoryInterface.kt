package edu.us.ischool.bchong.quizdroid

interface TopicRepositoryInterface {
    fun getTopic(topic: String): Topic
    fun getAllTopics(): List<String>
    //fun intializeTopics()
}