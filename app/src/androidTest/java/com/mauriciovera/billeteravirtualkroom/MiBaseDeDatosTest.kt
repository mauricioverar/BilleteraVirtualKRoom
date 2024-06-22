package com.mauriciovera.billeteravirtualkroom

import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import androidx.test.platform.app.InstrumentationRegistry
import com.mauriciovera.billeteravirtualkroom.model.local.dao.DatosDao
import com.mauriciovera.billeteravirtualkroom.model.local.database.DatosDatabase
import com.mauriciovera.billeteravirtualkroom.model.local.entities.DatosEntity
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MiBaseDeDatosTest {
    private lateinit var miBaseDeDatos: DatosDatabase
    private lateinit var datosDao: DatosDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        miBaseDeDatos =
            Room.inMemoryDatabaseBuilder(context, DatosDatabase::class.java)
                .build()
        datosDao = miBaseDeDatos.datosDao()
    }

    @Test
    fun testInsertarYRecuperarDatos() = runBlocking {//: Unit
        val datosEntity = listOf(DatosEntity
            (2,
            "Camila",
            "05/06/2024",
            120.0,
            "image")
        )
        datosDao.insertAll(datosEntity)
// Recuperar el dato insertado
        val datoRecuperado =
            datosDao.getAll()

        UiThreadStatement.runOnUiThread {
            val datossObserver = Observer<List<DatosEntity>> { datosList ->

                ViewMatchers.assertThat(datosList, CoreMatchers.not(emptyList()))
                Assert.assertEquals(2, datosList.size)
            }

            datoRecuperado.observeForever(datossObserver)

            datoRecuperado.removeObserver(datossObserver)
        }


    }

    @After
    fun tearDown() {
// Cerrar la base de datos después de cada prueba
        miBaseDeDatos.close()
    }
}