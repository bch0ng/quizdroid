package edu.us.ischool.bchong.quizdroid

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(var myDataset: List<String>) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    var onCategoryClickedListener: ((position: Int, category: String) -> Unit)? = null

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class CategoryHolder(val textView: TextView) : RecyclerView.ViewHolder(textView) {
        fun bindView(categoryName: String, position: Int) {
            textView.text = categoryName
            textView.setOnClickListener {
                onCategoryClickedListener?.invoke(position, categoryName)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.CategoryHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_text_view, parent, false) as TextView
        return CategoryHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bindView(myDataset[position], position)
        // holder.textView.text = myDataset[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}