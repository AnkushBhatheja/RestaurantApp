package com.zomato.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zomato.R
import com.zomato.ZomatoApplication
import com.zomato.model.Restaurant
import com.zomato.repo.RestaurantRepository
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class FavRestaurantsViewModel
@Inject constructor(
    var application: ZomatoApplication,
    var repository: RestaurantRepository
) : BaseViewModel(application) {

    val favouriteRestaurants: MutableLiveData<List<Restaurant>> by lazy {
        MutableLiveData<List<Restaurant>>()
    }


    fun fetchFavouriteRestaurants() {
        repository.fetchFavouriteRestaurants()
            .subscribe(object : SingleObserver<List<Restaurant>> {
                override fun onSuccess(restaurants: List<Restaurant>) {
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
            })
    }

    fun markFavourite(restaurant: Restaurant) {
        repository.markFavourite(restaurant)
//            .subscribe(object : SingleObserver<Restaurant> {
//                override fun onSuccess(restaurants: Restaurant) {
//                    mLoading.postValue(Pair(false, null))
////                    favouriteRestaurants.postValue(restaurants)
//                }
//
//                override fun onSubscribe(d: Disposable) {
//                    mLoading.postValue(Pair(true, application.getString(R.string.loading)))
//                }
//
//                override fun onError(e: Throwable) {
//                    mLoading.postValue(Pair(false, null))
//                    mShowMessage.postValue(e.message)
//                }
//            })
    }

}