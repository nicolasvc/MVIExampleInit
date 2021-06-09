package com.example.mviexampleinit.api

import com.example.mviexampleinit.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Object en kotlin es el igual a crear un singleton
 * */
object MyRetrofitBuilder {

    /*
    * By lazy significa que solo se va a instanciar una vez ya que cuando se vuelva a necesitar va a volver a usar esta instancia creada
    * */
    const val BASE_URL = "https://open-api.xyz/"
    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
    }

    val apiService: ApiService by lazy {
        retrofitBuilder.build().create(ApiService::class.java)
    }

}