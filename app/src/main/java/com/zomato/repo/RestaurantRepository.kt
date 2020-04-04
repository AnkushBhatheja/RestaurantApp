package com.zomato.repo

import com.zomato.model.Restaurant
import com.zomato.model.RestaurantData
import com.zomato.model.SearchResult
import io.reactivex.Observable
import io.reactivex.Single

interface RestaurantRepository {

    fun fetchFavouriteRestaurants(): Observable<List<RestaurantData>>

    fun fetchAllRestaurants(query: String?): Single<List<RestaurantData>>

    fun addToFavourite(restaurant: RestaurantData): Single<Unit>

    fun deleteFromFavourite(restaurant: RestaurantData): Single<Unit>
}