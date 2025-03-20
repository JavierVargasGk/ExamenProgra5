package com.Mendez.ulatina.houseManagerJavierVargas.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.Mendez.ulatina.houseManagerJavierVargas.data.database.interfaces.HouseDao
import com.Mendez.ulatina.houseManagerJavierVargas.model.HouseJavier

@Database(entities = [HouseJavier::class], version = 2, exportSchema = false)
abstract class HouseDataBase : RoomDatabase()
{

    abstract fun HouseDao(): HouseDao

    companion object
    {
        @Volatile
        private var INSTANCE: HouseDataBase?=null

        fun getDatabase(context: Context): HouseDataBase
        {
            return INSTANCE?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HouseDataBase::class.java,
                    "house_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }

    }
}