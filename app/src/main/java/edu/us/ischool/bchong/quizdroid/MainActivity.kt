package edu.us.ischool.bchong.quizdroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


private const val TOPIC_NAME = "param1"

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        val repo = QuizApp.getInstance().accessRepo()

        val topics = repo.getAllTopics()

        viewManager = LinearLayoutManager(this)
        viewAdapter = CategoryAdapter(topics)

        recyclerView = my_recycler_view
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter

        (viewAdapter as CategoryAdapter).onCategoryClickedListener = { _, name ->
            //Toast.makeText(this, "$name clicked!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra(TOPIC_NAME, name)
            }
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.my_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_settings -> {
                Log.i("BRANDON", "Main Activity")
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}