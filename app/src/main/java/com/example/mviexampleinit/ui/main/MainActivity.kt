package com.example.mviexampleinit.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mviexampleinit.R
import com.example.mviexampleinit.util.DataState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DataStateListener {


    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        showMainFragment()
    }


    private fun showMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment(), "MainFragment").commit()
    }

    override fun onDataStateChange(dataState: DataState<*>?) {
        handleDataStateError(dataState)
    }

    private fun handleDataStateError(dataState: DataState<*>?) {
        dataState?.let {
            //Handle loading
            showProgressBr(it.loading)
            //Handle Error
            it.message?.let { messageLive ->
                messageLive.getContentIfNotHandled()?.let{message ->
                    showToast(message)
                }
            }


        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgressBr(isVisible: Boolean) {
        if (isVisible)
            progress_bar.visibility = View.VISIBLE
        else
            progress_bar.visibility = View.GONE
    }


}