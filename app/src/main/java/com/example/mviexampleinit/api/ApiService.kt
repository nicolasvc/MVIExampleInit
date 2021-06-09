package com.example.mviexampleinit.api

import androidx.lifecycle.LiveData
import com.example.mviexampleinit.model.Blog
import com.example.mviexampleinit.model.User
import com.example.mviexampleinit.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/user/{userId}")
    fun getUser(@Path("userId") userId: String): LiveData<GenericApiResponse<User>>


    @GET("placeholder/blogs")
    fun getBlog(): LiveData<GenericApiResponse<List<Blog>>>
}