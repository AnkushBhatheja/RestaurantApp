package com.zomato.repo

import android.text.TextUtils
import com.zomato.database.ListItem
import com.zomato.database.RestaurantDao
import com.zomato.model.Restaurant
import com.zomato.model.RestaurantData
import com.zomato.model.SearchResult
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantRepositoryImpl @Inject constructor(
    private val restaurantService: RestaurantService,
    private val restaurantDao: RestaurantDao
) : RestaurantRepository {

    override fun fetchFavouriteRestaurants(): Observable<List<RestaurantData>> {
        return restaurantDao.getFavouriteRestaurant()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchAllRestaurants(query: String?): Single<List<RestaurantData>> {

        return Single.zip(
            restaurantService.fetchRestaurants(query)
                .map { it.restaurants }.subscribeOn(Schedulers.io()),
            fetchFavouriteRestaurants().elementAt(0, mutableListOf()),
            BiFunction<List<RestaurantData>,
                    List<RestaurantData>,
                    List<RestaurantData>>
            { all, fav ->
                for (item1 in all) {
                    if (fav.contains(item1))
                        item1.restaurant.isFavourite = true
                }
                all
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    override fun addToFavourite(restaurant: RestaurantData): Single<Unit> {
        return restaurantDao.addToFavourite(restaurant)
            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteFromFavourite(restaurant: RestaurantData): Single<Unit> {
        return restaurantDao.deleteFromFavourite(restaurant)
            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
    }

}