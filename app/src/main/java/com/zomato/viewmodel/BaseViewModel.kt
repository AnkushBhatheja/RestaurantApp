package com.zomato.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zomato.ZomatoApplication
import com.zomato.database.ListItem
import com.zomato.model.Cuisine
import com.zomato.model.Restaurant
import com.zomato.model.RestaurantData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel(application: ZomatoApplication) : AndroidViewModel(application) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val mLoading: MutableLiveData<Pair<Boolean, String?>> = MutableLiveData()

    val mShowMessage: MutableLiveData<String> = MutableLiveData()


    protected fun addDisposable(observable: Disposable) {
        compositeDisposable.add(observable)
    }

    protected fun convertToList(map: Map<String, List<RestaurantData>>): List<ListItem> {

        val list = mutableListOf<ListItem>()
        for (pair in map.entries) {
            list.add(Cuisine(pair.key))
            for (restaurants in pair.value)
                list.add(restaurants)
        }
        return list
    }

    protected fun groupByCuisine(list: List<RestaurantData>): Map<String, List<RestaurantData>> {
        val map = HashMap<String, MutableList<RestaurantData>>()
        for (restaurantData in list) {
            val cuisine = restaurantData.restaurant.cuisines
            if (map.containsKey(cuisine)) {
                map[cuisine]?.add(restaurantData)
            } else {
                map[cuisine] = mutableListOf<RestaurantData>().apply {
                    add(restaurantData)
                }
            }
        }
        return map
    }

}