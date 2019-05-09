package edu.us.ischool.bchong.quizdroid

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class SecondActivity : AppCompatActivity(),
        TopicOverviewFragment.OnBeginClickedListener,
        TopicQuestionFragment.OnSubmitClickedListener,
        TopicAnswerFragment.OnNextClickedListener
{

    private lateinit var topicTitle: TextView
    private lateinit var topicDescription: TextView
    private lateinit var beginButton: Button

    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction

    private var message = ""
    private var answer_count = 0
    private var question_index = 1
    private var question_count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        val intent = intent
        this.message = intent.getStringExtra("param1")

        this.fragmentManager = supportFragmentManager
        this.fragmentTransaction = this.fragmentManager.beginTransaction()

        val fragment = TopicOverviewFragment.newInstance(message)
        this.fragmentTransaction.add(R.id.second_activity, fragment)
        this.fragmentTransaction.commit()

    }

    override fun onBeginClicked(question_count: Int) {
        this.question_count = question_count
        val fragment2 = TopicQuestionFragment.newInstance(this.message, this.question_index)
        this.fragmentTransaction = this.fragmentManager.beginTransaction()
        this.fragmentTransaction.replace(R.id.second_activity, fragment2)
        this.fragmentTransaction.commit()
    }

    override fun onSubmitClicked(selectedAnswer: String, answer: String) {
        if (selectedAnswer == answer)
            this.answer_count++
        val fragment3 = TopicAnswerFragment.newInstance(selectedAnswer, answer, answer_count, question_count, question_index)
        this.fragmentTransaction = this.fragmentManager.beginTransaction()
        this.fragmentTransaction.replace(R.id.second_activity, fragment3)
        this.fragmentTransaction.commit()
    }

    override fun onNextClicked() {
        if (question_index == question_count) {
            this.finish()
        } else {
            this.question_index++
            val fragment4 = TopicQuestionFragment.newInstance(this.message, this.question_index)
            this.fragmentTransaction = this.fragmentManager.beginTransaction()
            this.fragmentTransaction.replace(R.id.second_activity, fragment4)
            this.fragmentTransaction.commit()
        }
    }
}
