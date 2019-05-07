package edu.us.ischool.bchong.quizdroid

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.RadioButton



private const val TOPIC_NAME = "param1"
private const val INDEX = "param2"

class TopicQuestionFragment : Fragment() {

    private lateinit var topicQuestion: TextView
    private lateinit var answerChoices: RadioGroup
    private lateinit var answerChoice1: RadioButton
    private lateinit var answerChoice2: RadioButton
    private lateinit var answerChoice3: RadioButton
    private lateinit var answerChoice4: RadioButton
    private lateinit var submitButton: Button

    private var topic: String? = null
    private var index: Int = -1
    private var questionCount: Int = 0

    var listener: OnSubmitClickedListener? = null

    interface OnSubmitClickedListener {
        fun onSubmitClicked(selectedAnswer: String, answer: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            topic = it.getString(TOPIC_NAME)
            index =it.getInt(INDEX)
        }
        Log.i("QUESTION", "TOPIC: $topic, QUESTION: $index")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnSubmitClickedListener
        if (listener == null) {
            throw ClassCastException("$context must implement OnSubmitClickedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_topic_question, container, false)

        topicQuestion = view.findViewById(R.id.topic_question)
        answerChoices = view.findViewById(R.id.answer_choices)
        answerChoice1 = view.findViewById(R.id.answer_choice1)
        answerChoice2 = view.findViewById(R.id.answer_choice2)
        answerChoice3 = view.findViewById(R.id.answer_choice3)
        answerChoice4 = view.findViewById(R.id.answer_choice4)

        var key = ""
        if (topic == "Math")
            key = "math"
        else if (topic == "Physics")
            key = "physics"
        else if (topic == "Marvel Heroes")
            key = "marvel"
        else if (topic == "Electronics")
            key = "electronics"

        val repo = QuizApp.getInstance().accessRepo()
        val topic = repo.getTopic(key)
        questionCount = topic.questions.size
        val quizQuestion = topic.questions.get(index - 1)
        topicQuestion.text = quizQuestion.questionText

        var answer = ""
        for (i in 1..4) {
            val radioButton = view.findViewById<RadioButton>(getResources().getIdentifier("answer_choice" + i, "id", activity?.packageName))
            var answerChoice = quizQuestion.answers.get(i - 1)
            if (i == quizQuestion.correctAnswerIndex + 1) {
                answer = answerChoice
            }
            radioButton.text = answerChoice
        }

        submitButton = view.findViewById(R.id.show_answer_button)

        answerChoices.setOnCheckedChangeListener { _, _ ->  submitButton.visibility = View.VISIBLE}

        submitButton.setOnClickListener {
            val radioButtonID = answerChoices.checkedRadioButtonId
            val radioButton = view.findViewById<RadioButton>(radioButtonID)
            val selectedIndex = answerChoices.indexOfChild(radioButton)
            val selectedRadio = answerChoices.getChildAt(selectedIndex) as RadioButton
            val selectedAnswer = selectedRadio.text.toString()
            listener?.onSubmitClicked(selectedAnswer, answer)
        }

        return view
    }


    companion object {
        @JvmStatic
        fun newInstance(topic: String, index: Int) =
            TopicQuestionFragment().apply {
                arguments = Bundle().apply {
                    putString(TOPIC_NAME, topic)
                    putInt(INDEX, index)
                }
            }
    }
}
