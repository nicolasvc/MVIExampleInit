package com.example.mviexampleinit.repository.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mviexampleinit.api.MyRetrofitBuilder
import com.example.mviexampleinit.model.Blog
import com.example.mviexampleinit.model.User
import com.example.mviexampleinit.ui.main.state.MainViewState
import com.example.mviexampleinit.util.*

/**
 *EL nombre MAIN hace alusión al paquete de la vista que va a interactura con este repositorio permite ser mas diciente sobre el codigo que se esta haciendo
 * */

object MainRepository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<List<Blog>,MainViewState>(){
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<Blog>>) {
              result.value = DataState.data(data = MainViewState(blogPost = response.body))
            }

            override fun createCall(): LiveData<GenericApiResponse<List<Blog>>> {
                return MyRetrofitBuilder.apiService.getBlog()
            }
        }.asLiveData()
    }

    fun getUser(idUser: String): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<User,MainViewState>(){
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(data = MainViewState(user = response.body))
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return MyRetrofitBuilder.apiService.getUser(idUser)
            }
        }.asLiveData()
    }


}
