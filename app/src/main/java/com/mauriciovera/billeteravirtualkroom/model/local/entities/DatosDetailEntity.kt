package com.mauriciovera.billeteravirtualkroom.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dato_detail_table")
data class DatosDetailEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val released: String,
    val rating: Double,
    val background_image: String
)
