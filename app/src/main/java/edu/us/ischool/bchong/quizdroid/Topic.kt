package edu.us.ischool.bchong.quizdroid

data class Topic(val title: String, val shortDescription: String,
                 val longDescription: String, val questions: List<Quiz>) {

}