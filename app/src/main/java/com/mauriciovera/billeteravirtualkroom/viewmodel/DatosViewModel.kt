package com.mauriciovera.billeteravirtualkroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mauriciovera.billeteravirtualkroom.model.local.database.DatosDatabase
import com.mauriciovera.billeteravirtualkroom.model.local.entities.DatosDetailEntity
import com.mauriciovera.billeteravirtualkroom.model.local.entities.DatosEntity
import com.mauriciovera.billeteravirtualkroom.model.repository.DatosRepository
import kotlinx.coroutines.launch

class DatosViewModel(application: Application) : AndroidViewModel(application) {
    private val datosRepository: DatosRepository

    private val detailLiveData = MutableLiveData<DatosDetailEntity>()

    init {
        val bd = DatosDatabase.getDatabase(application)
        val datosDao = bd.datosDao()
        datosRepository = DatosRepository(datosDao)
        viewModelScope.launch {
            datosRepository.getDatos()
        }
    }

    fun getDatos(): LiveData<List<DatosEntity>> = datosRepository.datosList

    fun getDetail(): LiveData<DatosDetailEntity> = detailLiveData

    fun getDetail(id: Int) = viewModelScope.launch {
        val detail = datosRepository.getDetail(id)
        detail?.let {
            detailLiveData.postValue(it)//SecondFragment
        }
    }
}