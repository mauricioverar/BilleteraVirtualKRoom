package com.mauriciovera.billeteravirtualkroom.model.network

import com.mauriciovera.billeteravirtualkroom.model.Constants
import com.mauriciovera.billeteravirtualkroom.model.DatoDetailModel
import com.mauriciovera.billeteravirtualkroom.model.DatoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DatosApiService {
    @GET("games")
    suspend fun getDatos(): Response<List<DatoModel>>//Response<List<Model>>

    @GET("games/{id}")
    suspend fun getDatosDetail(@Path("id")id: Int): Response<DatoDetailModel>//Response<DetailModel>
}