package com.zomato.repo

import android.text.TextUtils
import com.zomato.database.RestaurantDao
import com.zomato.model.Restaurant
import com.zomato.model.RestaurantData
import com.zomato.model.SearchResult
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantRepositoryImpl @Inject constructor(
    private val restaurantService: RestaurantService,
    private val restaurantDao: RestaurantDao
) : RestaurantRepository {

    override fun fetchFavouriteRestaurants(): Single<List<Restaurant>> {
        return restaurantDao.getFavouriteRestaurant()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchRestaurants(query: String?): Single<List<RestaurantData>> {
        return restaurantService.fetchRestaurants(query)
            .map {
                it.restaurants
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun markFavourite(restaurant: Restaurant): Single<Unit> {
        return restaurantDao.markFavourite(restaurant)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }


}