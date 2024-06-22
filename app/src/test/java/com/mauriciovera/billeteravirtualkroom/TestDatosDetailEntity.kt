package com.mauriciovera.billeteravirtualkroom

import com.mauriciovera.billeteravirtualkroom.model.local.entities.DatosDetailEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TestDatosDetailEntity {
    private lateinit var detailEntity: DatosDetailEntity

    @Before
    fun setup(){
        detailEntity = DatosDetailEntity(
            id = 2,
            name = "Camila ",
            released = "05/06/2024",
            rating = 120.0,
            background_image = "image"
        )
    }
    @After
    fun tearDown(){   }// acciones de limpieza de liberacion de recursos

    @Test
    fun testDatoConstructor(){
        // verificar los valores asignados
        assert(detailEntity.id == 2)
        assert(detailEntity.name == "Camila ")
        assert(detailEntity.released== "05/06/2024")
        assert(detailEntity.rating == 120.0)
        assert(detailEntity.background_image== "image" )
    }
}