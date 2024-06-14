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

class DatosViewModel (application: Application): AndroidViewModel(application) {
    // :
    private val datosRepository: DatosRepository

    // =
    private val detailLiveData = MutableLiveData<DatosDetailEntity>()

    //private var isSelected : String = "-1"// :Int = 0

    init { // inicia con todos los datos para la lista del recycler
        //basedatos
        val bd = DatosDatabase.getDatabase(application) // clic dentro del archivo para import
        //dao
        val datosDao = bd.datosDao()
        datosRepository = DatosRepository(datosDao)
        viewModelScope.launch {
            datosRepository.getDatos()
        }
    }

    // obtener la lista de datos de la base de datos en local
    fun getDatos(): LiveData<List<DatosEntity>> = datosRepository.datosList

    // obtener el detalle del dato, de la base de datos en local
    fun getDetail(): LiveData<DatosDetailEntity> = detailLiveData

    // obtener el detalle del dato, de la base de datos en local
    fun getDetail(id: Int) = viewModelScope.launch {
        val detail = datosRepository.getDetail(id)
        // importante **********
        detail?.let {
            detailLiveData.postValue(it)//SecondFragment
        }
    }
}