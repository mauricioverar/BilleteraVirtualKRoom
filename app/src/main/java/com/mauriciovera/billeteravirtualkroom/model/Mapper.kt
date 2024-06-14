package com.mauriciovera.billeteravirtualkroom.model

import com.mauriciovera.billeteravirtualkroom.model.local.entities.DatosDetailEntity
import com.mauriciovera.billeteravirtualkroom.model.local.entities.DatosEntity

fun fromInternetDatosEntity(datosList: List<DatoModel>): List<DatosEntity> {
    return datosList.map {
        DatosEntity(
            id = it.id,
            name = it.name,
            released = it.released,
            //rating = it.rating,
            background_image = it.background_image
        )
    }
}

fun fromInternetDetailEntity(dato: DatoDetailModel): DatosDetailEntity {
    return DatosDetailEntity(
        id = dato.id,
        name = dato.name,
        released = dato.released,
        rating = dato.rating,
        background_image = dato.background_image
    )
}