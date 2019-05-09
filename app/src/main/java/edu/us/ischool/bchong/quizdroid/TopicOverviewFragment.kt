package edu.us.ischool.bchong.quizdroid

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

private const val TOPIC_NAME = "param1"

class TopicOverviewFragment : Fragment() {

    private lateinit var topicTitle: TextView
    private lateinit var topicDescription: TextView
    private lateinit var beginButton: Button

    private var topic: String = ""
    private var question_count: Int = 0

    var listener: OnBeginClickedListener? = null

    interface OnBeginClickedListener {
        fun onBeginClicked(question_count: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            topic = it.getString(TOPIC_NAME)
        }
        Log.i("OVERVIEW", "TOPIC: " + topic)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnBeginClickedListener
        if (listener == null) {
            throw ClassCastException("$context must implement OnBeginClickedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_topic_overview, container, false)

        topicTitle = view.findViewById(R.id.topic_title)
        val topicTitleString = "Topic: " + topic
        topicTitle.text = topicTitleString

        topicDescription = view.findViewById(R.id.topic_description)

        val repo = QuizApp.getInstance().accessRepo()

        var description = "Description:\n"
        val key = repo.getTopic(topic)
        question_count = key.questions.size
        description += key.shortDescription +
                "\n\nThis quiz has " + question_count +
                (if (question_count === 1) " question." else " questions.")

        topicDescription.text = description

        beginButton = view.findViewById(R.id.begin_button)

        beginButton.setOnClickListener {
            listener?.onBeginClicked(question_count)
        }

        return view
    }


    companion object {
        @JvmStatic
        fun newInstance(topic: String) =
            TopicOverviewFragment().apply {
                arguments = Bundle().apply {
                    putString(TOPIC_NAME, topic)
                }
            }
    }
}
