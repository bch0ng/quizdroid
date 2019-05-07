package edu.us.ischool.bchong.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        viewAdapter = CategoryAdapter(listOf("Math", "Physics", "Marvel Heroes", "Electronics"))

        recyclerView = my_recycler_view.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        (viewAdapter as CategoryAdapter).onCategoryClickedListener = { position, name ->
            //oast.makeText(this, "$name clicked!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, name)
            }
            startActivity(intent)
        }
    }
}