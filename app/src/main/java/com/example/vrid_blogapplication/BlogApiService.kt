package com.example.vrid_blogapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BlogApiService {
    @GET("/wp-json/wp/v2/posts")     //Get Request followed by an endpoint
    fun getBlogPosts(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Call<List<BlogPost>>
}
