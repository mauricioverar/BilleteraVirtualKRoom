package com.mauriciovera.billeteravirtualkroom.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mauriciovera.billeteravirtualkroom.model.local.entities.DatosDetailEntity
import com.mauriciovera.billeteravirtualkroom.model.local.entities.DatosEntity

@Dao
interface DatosDao {
    @Query("SELECT * FROM datos_list_table ORDER BY id ASC") // list
    fun getAll(): LiveData<List<DatosEntity>>

    @Query("SELECT * FROM dato_detail_table WHERE id = :id") // detail
    fun getById(id: Int): LiveData<DatosDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(datosList: List<DatosEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetail(dato: DatosDetailEntity)
}