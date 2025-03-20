package com.Mendez.ulatina.houseManagerJavierVargas.di

import android.content.Context
import androidx.lifecycle.LiveData
import com.Mendez.ulatina.houseManagerJavierVargas.data.database.HouseDataBase
import com.Mendez.ulatina.houseManagerJavierVargas.data.database.interfaces.HouseDao
import com.Mendez.ulatina.houseManagerJavierVargas.model.HouseJavier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ObjectModule {
    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): HouseDataBase {
        return HouseDataBase.getDatabase(context)
    }
    @Provides
    @Singleton
    fun providesItemDao(HouseDataBase: HouseDataBase): HouseDao {
        return HouseDataBase.HouseDao()
    }
    @Provides
    @Singleton
    fun providesItemRep(HouseDao: HouseDao): LiveData<List<HouseJavier>> {
        return HouseDao.getAll()
    }
}