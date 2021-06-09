package com.example.mviexampleinit.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mviexampleinit.model.Blog
import com.example.mviexampleinit.model.User
import com.example.mviexampleinit.repository.main.MainRepository
import com.example.mviexampleinit.ui.main.state.MainStateEvent
import com.example.mviexampleinit.ui.main.state.MainStateEvent.*
import com.example.mviexampleinit.ui.main.state.MainViewState
import com.example.mviexampleinit.util.AbsentLiveData
import com.example.mviexampleinit.util.DataState

class MainViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    /**
     * Transformation Sirve para que cuando cambie el estado lo escuche y con base en el tipo de evento retorne la respuesta necesario
     * **/
    val dataState: LiveData<DataState<MainViewState>> = Transformations.switchMap(_stateEvent) { stateEvent ->
        stateEvent?.let {
            handleStateEvent(it)
        }
    }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        return when (stateEvent) {
            is GetBlogPostsEvent -> {
                MainRepository.getBlogPosts()
            }
            is GetUserEvent -> {
                MainRepository.getUser(stateEvent.userId)
            }
            is None -> {
                AbsentLiveData.create()
            }
        }
    }

    fun setBlogListData(blogPost: List<Blog>) {
        val update = getCurrentViewStateOrNew()
        update.blogPost = blogPost
        _viewState.value = update
    }

    fun setUser(user: User) {
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    fun getCurrentViewStateOrNew(): MainViewState {
        return viewState.value ?: return MainViewState()
    }

    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }

}