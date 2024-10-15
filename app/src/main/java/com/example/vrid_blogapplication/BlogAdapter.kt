import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vrid_blogapplication.BlogPost
import com.example.vrid_blogapplication.R

// BlogAdapter class for RecyclerView
class BlogAdapter(
    private val blogList: List<BlogPost>, // Data source: List of blog posts
    private val onItemClick: (BlogPost) -> Unit // Function to handle item click
) : RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {

    // ViewHolder class representing each item
    inner class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textTitle) // Bind the TextView

        // Method to bind data to the view
        fun bind(blogPost: BlogPost) {
            textTitle.text = blogPost.title.rendered // Set the blog title

            // Handle item click, pass the clicked blog post to the provided lambda function
            itemView.setOnClickListener {
                onItemClick(blogPost)
            }
        }
    }

    // Inflate the item layout and return the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false) // Inflate the item layout
        return BlogViewHolder(itemView)
    }

    // Bind data to the ViewHolder at the specified position
    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blogPost = blogList[position] // Get the blog post at the current position
        holder.bind(blogPost) // Bind the blog post to the ViewHolder
    }

    // Return the total count of items
    override fun getItemCount(): Int {
        return blogList.size
    }
}
