package com.mauriciovera.billeteravirtualkroom.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "datos_list_table")
data class DatosEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val released: String,
    //val rating: Double,
    val background_image: String
)
