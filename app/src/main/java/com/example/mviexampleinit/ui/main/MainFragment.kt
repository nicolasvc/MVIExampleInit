package com.example.mviexampleinit.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mviexampleinit.R
import com.example.mviexampleinit.model.Blog
import com.example.mviexampleinit.model.User
import com.example.mviexampleinit.ui.main.state.MainStateEvent
import com.example.mviexampleinit.util.TopSpacinItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), MainRecyclerAdapter.Interaction {

    //region Properties
    private lateinit var viewModel: MainViewModel

    private lateinit var dataStateHandler: DataStateListener

    private lateinit var mainRecyclerAdapter: MainRecyclerAdapter
    //endregion

    //region Overload
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateHandler = context as DataStateListener
        } catch (e: ClassCastException) {
            println("Debug : $context muys implemente")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        /**
         * Esta es una buena forma de iniciar los viewModel en el fragmento para evitar actividades nulas o demas
         * */
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        subscriberObserver()
        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.get_blog -> triggerGetBlogsEvent()
            R.id.get_user -> triggerGetUserEvent()
        }
        return super.onOptionsItemSelected(item)
    }
    //endregion

    //region Propios

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacinItemDecoration = TopSpacinItemDecoration(30)
            addItemDecoration(topSpacinItemDecoration)
            mainRecyclerAdapter = MainRecyclerAdapter(this@MainFragment)
            adapter = mainRecyclerAdapter
        }
    }


    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(MainStateEvent.GetUserEvent("1"))
    }

    private fun triggerGetBlogsEvent() {
        viewModel.setStateEvent(MainStateEvent.GetBlogPostsEvent())
    }


    private fun subscriberObserver() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->

            // Handle Loading and Message
            dataStateHandler.onDataStateChange(dataState)

            // handle Data<T>
            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->

                    println("DEBUG: DataState: $mainViewState")

                    mainViewState.blogPost?.let {
                        // set BlogPosts data
                        viewModel.setBlogListData(it)
                    }

                    mainViewState.user?.let {
                        // set User data
                        viewModel.setUser(it)
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            viewState.blogPost?.let { blogPosts ->
                // set BlogPosts to RecyclerView
                println("DEBUG: Setting blog posts to RecyclerView: $blogPosts")
                mainRecyclerAdapter.submitList(blogPosts)
            }

            viewState.user?.let { user ->
                // set User data to widgets
                println("DEBUG: Setting User data: $user")
                setUserProperties(user)

            }
        })
    }

    private fun setUserProperties(user: User) {
        view?.let {
            Glide.with(this).load(user.image).into(image)
        }
        email.text = user.email
        username.text = user.username
    }

    //endregion

    //region OverloadAdapterRecycler
    override fun onItemSelected(position: Int, item: Blog) {
        println("Debug: the item was click")
    }
    //endregion
}