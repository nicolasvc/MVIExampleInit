package com.example.mviexampleinit.ui.main.state

import com.example.mviexampleinit.model.Blog
import com.example.mviexampleinit.model.User

data class MainViewState(
    var blogPost: List<Blog>? = null,
    var user: User? = null
)