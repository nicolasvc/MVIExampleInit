package com.example.mviexampleinit.ui.main

import com.example.mviexampleinit.util.DataState

interface DataStateListener {

    fun onDataStateChange(dataState:DataState<*>?)
}