package com.zomato.di

import androidx.room.Room
import com.zomato.RestaurantApplication
import com.zomato.database.Database
import com.zomato.database.RestaurantDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DBModule {
    @Provides
    fun provideRestaurantDao(database: Database): RestaurantDao {
        return database.restaurantDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(application: RestaurantApplication): Database {
        return Room
            .databaseBuilder(application.applicationContext, Database::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}