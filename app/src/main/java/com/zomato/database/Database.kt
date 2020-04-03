package com.zomato.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zomato.model.Restaurant

@Database(
    entities = [Restaurant::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
}