package com.example.mviexampleinit.repository.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.mviexampleinit.util.*
import com.example.mviexampleinit.util.Const.Companion.TESTING_NETWORK_DELAY
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class NetworkBoundResource<ResponseObject,ViewStateType> {

    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init {
        result.value = DataState.loading(true)
        GlobalScope.launch(IO){
            delay(TESTING_NETWORK_DELAY)
            withContext(Main){
                val apiResponde = createCall()

                result.addSource(apiResponde){response->
                    result.removeSource(apiResponde)
                    handleNetworkCall(response)
                }
            }
        }
    }

    private fun handleNetworkCall(response: GenericApiResponse<ResponseObject>?) {
         when (response) {
            is ApiSuccessResponse -> {
                handleApiSuccessResponse(response)
            }
            is ApiErrorResponse -> {
                onReturnError(response.errorMessage)
            }
            is ApiEmptyResponse -> {
                onReturnError("HTTP 204, Returned Nothing")
            }
        }
    }

    private fun onReturnError(message:String){
        result.value = DataState.error(message)
    }

    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    fun asLiveData () = result as LiveData<DataState<ViewStateType>>

}