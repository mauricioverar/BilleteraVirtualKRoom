package com.mauriciovera.billeteravirtualkroom.model.network

import com.mauriciovera.billeteravirtualkroom.model.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private var instance: Retrofit? = null
    lateinit var getInstance: Retrofit

    fun getInstance(): Retrofit {
        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance!!
    }
}