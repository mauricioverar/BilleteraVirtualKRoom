package com.mauriciovera.billeteravirtualkroom.model.repository

import android.util.Log
import com.mauriciovera.billeteravirtualkroom.model.fromInternetDatosEntity
import com.mauriciovera.billeteravirtualkroom.model.fromInternetDetailEntity
import com.mauriciovera.billeteravirtualkroom.model.local.dao.DatosDao
import com.mauriciovera.billeteravirtualkroom.model.local.entities.DatosDetailEntity
import com.mauriciovera.billeteravirtualkroom.model.network.RetrofitClient

class DatosRepository(private val datosDao: DatosDao) {
    private val networkService = RetrofitClient.getInstance()
    val datosList = datosDao.getAll()

    suspend fun getDatos() {
        val service = kotlin.runCatching { networkService.getDatos() }
        service.onSuccess {
            when (it.code()) {
                in 200..299 -> it.body()?.let {
                    Log.d("datos", it.toString())
                    datosDao.insertAll(fromInternetDatosEntity(it))
                }

                else -> Log.d("Repository", "${it.code()}-${it.errorBody()}")
            }
            service.onFailure {
                Log.e("Error", "${it.message}")
            }
        }
    }

    suspend fun getDetail(id: Int): DatosDetailEntity? {
        val service = kotlin.runCatching { networkService.getDatosDetail(id) }
        return service.getOrNull()?.body()?.let { datosDetail ->
            val datosDetailEntity = fromInternetDetailEntity(datosDetail)
            datosDao.insertDetail(datosDetailEntity)
            datosDetailEntity
        }
    }
}