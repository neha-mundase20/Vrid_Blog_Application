package com.example.vrid_blogapplication

import BlogAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var blogAdapter: BlogAdapter
    private var blogList = mutableListOf<BlogPost>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewBlogs)
        blogAdapter = BlogAdapter(blogList) { blogPost ->
            // Handle item click
            val intent = Intent(this, BlogDetail::class.java)
            intent.putExtra("url", blogPost.link)
            startActivity(intent)
        }
        recyclerView.adapter = blogAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch blog data
        fetchBlogPosts()
    }

    private fun fetchBlogPosts() {
        val apiService = RetrofitClient.instance
        apiService.getBlogPosts(10, 1).enqueue(object : retrofit2.Callback<List<BlogPost>> {
            override fun onResponse(
                call: Call<List<BlogPost>>,
                response: retrofit2.Response<List<BlogPost>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { posts ->
                        blogList.addAll(posts)
                        blogAdapter.notifyDataSetChanged() // Notify adapter about data changes
                    }
                } else {
                    // Handle response errors
                    Log.e("MainActivity", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<BlogPost>>, t: Throwable) {
                // Handle failure
                Log.e("MainActivity", "Failure: ${t.message}")
            }
        })
    }

    override fun onBackPressed() {
        // Create an AlertDialog builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit")
        builder.setMessage("Are you sure you want to exit?")

        // Set positive button (Yes)
        builder.setPositiveButton("Yes") { dialog, _ ->
            dialog.dismiss()
            super.onBackPressed() // Exit the activity
            finishAffinity() // This will close all activities and exit the app
            exitProcess(0)
        }

        // Set negative button (No)
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss() // Dismiss the dialog
        }
        // Show the dialog
        builder.create().show()
    }
}
