package com.Mendez.ulatina.houseManagerJavierVargas.data.database.interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.Mendez.ulatina.houseManagerJavierVargas.model.HouseJavier

@Dao
interface  HouseDao{
    @Insert
    suspend fun insert(HouseJavier : HouseJavier)
    @Update
    suspend fun  update(HouseJavier: HouseJavier)
    @Delete
    suspend fun  delete(houseJavier: HouseJavier)
    @Query("DELETE FROM HOUSEJAVIER")
    fun DropTable()
    @Query("SELECT * FROM HOUSEJAVIER ORDER BY id")
    fun getAll(): LiveData<List<HouseJavier>>
}