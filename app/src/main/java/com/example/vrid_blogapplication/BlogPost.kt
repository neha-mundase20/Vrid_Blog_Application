package com.example.vrid_blogapplication

data class BlogPost(
    val id: Int,
    val date: String,
    val slug: String,
    val status: String,
    val type: String,
    val link: String,
    val title: Rendered,
    val content: Rendered,
    val imageUrl: String // for the image link
)

data class Rendered(
    val rendered: String
)


