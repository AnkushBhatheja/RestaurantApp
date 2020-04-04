package com.zomato.database

import androidx.room.*
import com.zomato.model.Restaurant
import com.zomato.model.RestaurantData
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToFavourite(restaurantData: RestaurantData): Single<Unit>

    @Delete
    fun deleteFromFavourite(restaurantData: RestaurantData): Single<Unit>

    @Query("select * from RestaurantData")
    fun getFavouriteRestaurant(): Observable<MutableList<RestaurantData>>
}