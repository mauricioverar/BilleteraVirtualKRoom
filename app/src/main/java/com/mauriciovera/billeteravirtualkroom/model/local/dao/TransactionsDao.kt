package com.mauriciovera.billeteravirtualkroom.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mauriciovera.billeteravirtualkroom.model.local.entities.DatosDetailEntity
import com.mauriciovera.billeteravirtualkroom.model.local.entities.DatosEntity
import com.mauriciovera.billeteravirtualkroom.model.local.entities.TransactionsDetailEntity
import com.mauriciovera.billeteravirtualkroom.model.local.entities.TransactionsEntity

interface TransactionsDao {
    @Query("SELECT * FROM transactions_list_table ORDER BY id ASC") // list
    fun getAll(): LiveData<List<TransactionsEntity>>

    @Query("SELECT * FROM transaction_detail_table WHERE id = :id") // detail
    fun getById(id: Int): LiveData<TransactionsDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(datosList: List<TransactionsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetail(dato: TransactionsDetailEntity)
}