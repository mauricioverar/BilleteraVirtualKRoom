package com.mauriciovera.billeteravirtualkroom.model.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mauriciovera.billeteravirtualkroom.model.local.dao.DatosDao
import com.mauriciovera.billeteravirtualkroom.model.local.entities.DatosDetailEntity
import com.mauriciovera.billeteravirtualkroom.model.local.entities.DatosEntity

@Database(entities = [DatosEntity::class, DatosDetailEntity::class], version = 1)
abstract class DatosDatabase: RoomDatabase() {
    abstract fun datosDao(): DatosDao

    companion object {
        @Volatile
        private var INSTANCE: DatosDatabase? = null

        fun getDatabase(context: Context): DatosDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatosDatabase::class.java,
                    "wallet_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}