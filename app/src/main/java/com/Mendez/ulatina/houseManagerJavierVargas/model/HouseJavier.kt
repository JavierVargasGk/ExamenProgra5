package com.Mendez.ulatina.houseManagerJavierVargas.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "HouseJavier")
data class HouseJavier(
    @PrimaryKey(autoGenerate = true)
        val id : Long = 0,
        val name : String = "",
        val descripcionHouse : String = "",
        val sqrMeters : Double = 0.00,
        val Apellido2 : String = "",
        val IsSelected : Boolean = false
)
