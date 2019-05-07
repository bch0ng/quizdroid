package edu.us.ischool.bchong.quizdroid

interface TopicRepositoryInterface {
    fun getTopic(topicKey: String): Topic
    fun getAllTopics(): List<String>
}