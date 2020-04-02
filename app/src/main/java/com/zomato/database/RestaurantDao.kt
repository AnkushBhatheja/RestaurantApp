package com.zomato.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zomato.model.Restaurant

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun markFavourite(restaurant: Restaurant)

    @Query("select * from Restaurant")
    fun getFavouriteRestaurant(): List<Restaurant>
}