package edu.us.ischool.bchong.quizdroid

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

private const val SELECTED_ANSWER = "param1"
private const val ANSWER = "param2"
private const val CORRECT_COUNT = "param3"
private const val QUESTION_COUNT = "param4"
private const val QUESTION_INDEX = "param5"

class TopicAnswerFragment : Fragment() {

    private lateinit var topicAnswer: TextView
    private lateinit var topicChosenAnswer: TextView
    private lateinit var topicQuizStats: TextView
    private lateinit var nextButton: Button

    private var selectedAnswer: String = ""
    private var answer: String = ""
    private var correctCount: Int = 0
    private var questionCount: Int = 0
    private var questionIndex: Int = 0

    var listener: OnNextClickedListener? = null

    interface OnNextClickedListener {
        fun onNextClicked()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedAnswer = it.getString(SELECTED_ANSWER)
            answer = it.getString(ANSWER)
            correctCount = it.getInt(CORRECT_COUNT)
            questionCount = it.getInt(QUESTION_COUNT)
            questionIndex = it.getInt(QUESTION_INDEX)
        }
        Log.i("ANSWER", "HELLO WORLD")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnNextClickedListener
        if (listener == null) {
            throw ClassCastException("$context must implement OnNextClickedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_topic_answer, container, false)

        topicAnswer = view.findViewById(R.id.topic_answer)
        topicChosenAnswer = view.findViewById(R.id.topic_chosen_answer)
        topicQuizStats = view.findViewById(R.id.quiz_stats)
        nextButton = view.findViewById(R.id.answer_next_button)
        if (questionIndex == questionCount) {
            nextButton.text = "Finish"
        }

        val topicAnswerString = "Correct Answer: " + this.answer
        topicAnswer.text = topicAnswerString
        val topicChosenAnswerString = "Your Answer: " + this.selectedAnswer
        topicChosenAnswer.text = topicChosenAnswerString
        val quizStatString = "You have " + this.correctCount + " out of " + this.questionIndex + " correct"
        topicQuizStats.text = quizStatString

        nextButton.setOnClickListener {
            listener?.onNextClicked()
        }

        return view
    }


    companion object {
        @JvmStatic
        fun newInstance(selectedAnswer: String, answer: String,
                        correctCount: Int, questionCount: Int,
                        questionIndex: Int) =
            TopicAnswerFragment().apply {
                arguments = Bundle().apply {
                    putString(SELECTED_ANSWER, selectedAnswer)
                    putString(ANSWER, answer)
                    putInt(CORRECT_COUNT, correctCount)
                    putInt(QUESTION_COUNT, questionCount)
                    putInt(QUESTION_INDEX, questionIndex)
                }
            }
    }
}
