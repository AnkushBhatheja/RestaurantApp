package com.zomato.repo

import com.zomato.model.Restaurant
import com.zomato.model.RestaurantData
import com.zomato.model.SearchResult
import io.reactivex.Single

interface RestaurantRepository {
    fun fetchFavouriteRestaurants(): Single<List<Restaurant>>

    fun fetchRestaurants(query: String?): Single<List<RestaurantData>>

    fun markFavourite(restaurant: Restaurant) : Single<Unit>
}