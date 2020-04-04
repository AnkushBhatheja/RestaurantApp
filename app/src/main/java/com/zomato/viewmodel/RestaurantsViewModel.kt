package com.zomato.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zomato.R
import com.zomato.RestaurantApplication
import com.zomato.SortEvent
import com.zomato.database.ListItem
import com.zomato.model.Cuisine
import com.zomato.model.RestaurantData
import com.zomato.repo.RestaurantRepository
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RestaurantsViewModel
@Inject constructor(
    var application: RestaurantApplication,
    var repository: RestaurantRepository
) : BaseViewModel(application) {

    private val publishSubject: PublishSubject<String?> = PublishSubject.create<String?>()

    val sortEvent: MutableLiveData<SortEvent> by lazy {
        MutableLiveData<SortEvent>()
    }
    val searchKey: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>("")
    }

    val restaurantsLiveData: MutableLiveData<MutableList<RestaurantData>> by lazy {
        MutableLiveData<MutableList<RestaurantData>>()
    }

    val favouriteRestaurants: MutableLiveData<MutableList<RestaurantData>> by lazy {
        MutableLiveData<MutableList<RestaurantData>>()
    }

    fun search(key: String) {
        publishSubject.onNext(key)
    }

    private fun fetchRestaurants(query: String?) {
        repository.fetchAllRestaurants(query)
            .map {
                sortRestaurant(it)
            }
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<MutableList<RestaurantData>> {
                override fun onSuccess(data: MutableList<RestaurantData>) {
                    mLoading.postValue(Pair(false, null))
                    restaurantsLiveData.postValue(data)
                }

                override fun onSubscribe(d: Disposable) {
                    mLoading.postValue(
                        Pair(
                            true,
                            application.getString(R.string.loading)
                        )
                    )
                }

                override fun onError(e: Throwable) {
                    mLoading.postValue(Pair(false, null))
                    mShowMessage.postValue(e.message)
                }
            })

    }


    fun getAllRestaurants() {
        addDisposable(publishSubject.debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                fetchRestaurants(it)
            })
    }

    fun fetchFavouriteRestaurants() {
        repository.fetchFavouriteRestaurants()
            .map {
                sortRestaurant(it)
            }
            .subscribe(object : Observer<MutableList<RestaurantData>> {

                override fun onNext(restaurants: MutableList<RestaurantData>) {
                    mLoading.postValue(Pair(false, null))
                    favouriteRestaurants.postValue(restaurants)
                }

                override fun onSubscribe(d: Disposable) {
                    mLoading.postValue(Pair(true, application.getString(R.string.loading)))
                }

                override fun onError(e: Throwable) {
                    mLoading.postValue(Pair(false, null))
                    mShowMessage.postValue(e.message)
                }

                override fun onComplete() {
                    mLoading.postValue(Pair(false, null))
                }


            })
    }

    fun addToFavourite(restaurant: RestaurantData) {
        repository.addToFavourite(restaurant)
            .subscribe()
    }

    fun deleteFromFavourite(restaurant: RestaurantData) {
        repository.deleteFromFavourite(restaurant)
            .subscribe()
    }


    private fun convertToList(map: HashMap<String, MutableList<RestaurantData>>): List<ListItem> {
        val list = mutableListOf<ListItem>()
        for (pair in map.entries) {
            list.add(Cuisine(pair.key))
            for (restaurants in pair.value)
                list.add(restaurants)
        }
        return list
    }

    private fun groupByCuisine(list: List<RestaurantData>?): HashMap<String, MutableList<RestaurantData>> {
        val map = HashMap<String, MutableList<RestaurantData>>()
        if (list != null) {
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
        }
        return map
    }

    fun sortRestaurant(restaurant: MutableList<RestaurantData>?): MutableList<RestaurantData> {
        restaurant?.sortWith(kotlin.Comparator { r1, r2 ->
            when (sortEvent.value) {
                SortEvent.PRICE -> r1.restaurant.avgPrice - r2.restaurant.avgPrice
                SortEvent.RATING -> ((r1.restaurant.ratingData.avgRating - r2.restaurant.ratingData.avgRating) * 10).toInt()
                else -> 0
            }
        })
        return mutableListOf<RestaurantData>().apply {
            restaurant?.let { this.addAll(it) }
        }
    }

    fun getDataToDisplay(value: List<RestaurantData>?) = convertToList(groupByCuisine(value))


}