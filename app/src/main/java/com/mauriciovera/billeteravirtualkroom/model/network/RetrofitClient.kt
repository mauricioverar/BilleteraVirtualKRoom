package com.mauriciovera.billeteravirtualkroom.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        private const val BASE_URL = "https://my-json-server.typicode.com/himuravidal/gamesDB/"
        lateinit var  getInstance : Retrofit

        fun getInstance(): DatosApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(DatosApiService::class.java)
        }
    }
}