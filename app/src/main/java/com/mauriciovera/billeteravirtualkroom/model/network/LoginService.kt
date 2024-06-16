package com.mauriciovera.billeteravirtualkroom.model.network

import com.mauriciovera.billeteravirtualkroom.model.Constants
import com.mauriciovera.billeteravirtualkroom.model.UserModel
import com.mauriciovera.billeteravirtualkroom.model.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @Headers("Content-Type: application/json")
    @POST(Constants.API_PATH + Constants.LOGIN_PATH)
    fun login(@Body data: UserModel): Call<LoginResponse>

    /*@POST("login") // Reemplaza con el endpoint correcto de tu API
    fun login(@Body user: User): Call<LoginResponse>*/
}