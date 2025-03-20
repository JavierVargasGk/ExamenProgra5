package com.Mendez.ulatina.houseManagerJavierVargas.data.repository

import androidx.lifecycle.LiveData
import javax.inject.Inject
import com.Mendez.ulatina.houseManagerJavierVargas.data.database.interfaces.HouseDao
import com.Mendez.ulatina.houseManagerJavierVargas.model.HouseJavier


class HouseRepository @Inject constructor(private val houseDao: HouseDao) {
    fun getAll() : LiveData<List<HouseJavier>> {
        return houseDao.getAll()
    }
    suspend fun insert(houseJavier: HouseJavier){
        houseDao.insert(houseJavier)
    }
    suspend fun update(houseJavier: HouseJavier){
        houseDao.update(houseJavier)
    }
    suspend fun delete(houseJavier: HouseJavier){
        houseDao.delete(houseJavier)
    }
    fun deleteAll(){
        houseDao.DropTable()
    }

}