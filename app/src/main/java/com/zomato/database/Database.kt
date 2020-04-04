package com.zomato.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zomato.model.Restaurant
import com.zomato.model.RestaurantData

@Database(
    entities = [RestaurantData::class],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
}