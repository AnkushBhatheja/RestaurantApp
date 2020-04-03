package com.zomato.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zomato.R
import com.zomato.ZomatoApplication
import com.zomato.database.ListItem
import com.zomato.model.Cuisine
import com.zomato.model.Restaurant
import com.zomato.model.RestaurantData
import com.zomato.repo.RestaurantRepository
import io.reactivex.Scheduler
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RestaurantsViewModel
@Inject constructor(
    var application: ZomatoApplication,
    var repository: RestaurantRepository
) : BaseViewModel(application) {

    private val publishSubject: PublishSubject<String?> = PublishSubject.create<String?>()

    val searchKey: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>("")
    }

    val restaurantsLiveData: MutableLiveData<List<ListItem>> by lazy {
        MutableLiveData<List<ListItem>>()
    }


    fun search(key: String) {
        publishSubject.onNext(key)
    }

    private fun fetchRestaurants(query: String?) {
        repository.fetchRestaurants(query)
            .subscribeOn(Schedulers.io())
            .map {
                convertToList(groupByCuisine(it))
            }
            .subscribe(object : SingleObserver<List<ListItem>> {
                override fun onSuccess(data: List<ListItem>) {
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

    private fun convertToList(map: Map<String, List<Restaurant>>): List<ListItem> {

        val list = mutableListOf<ListItem>()
        for (pair in map.entries) {
            list.add(Cuisine(pair.key))
            for (restaurants in pair.value)
                list.add(restaurants)
        }
        return list
    }

    private fun groupByCuisine(list: List<RestaurantData>): Map<String, List<Restaurant>> {
        val map = HashMap<String, MutableList<Restaurant>>()
        for (restaurant in list) {
            val cuisine = restaurant.restaurant.cuisines
            if (map.containsKey(cuisine)) {
                map[cuisine]?.add(restaurant.restaurant)
            } else {
                map[cuisine] = mutableListOf<Restaurant>().apply {
                    add(restaurant.restaurant)
                }
            }
        }
        return map
    }


    fun fetchRestaurants() {
        addDisposable(publishSubject.debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                fetchRestaurants(it)
            })
    }

}