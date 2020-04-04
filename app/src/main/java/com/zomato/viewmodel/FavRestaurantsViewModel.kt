package com.zomato.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zomato.R
import com.zomato.ZomatoApplication
import com.zomato.database.ListItem
import com.zomato.model.Restaurant
import com.zomato.repo.RestaurantRepository
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class FavRestaurantsViewModel
@Inject constructor(
    var application: ZomatoApplication,
    var repository: RestaurantRepository
) : BaseViewModel(application) {


    val favouriteRestaurants: MutableLiveData<List<ListItem>> by lazy {
        MutableLiveData<List<ListItem>>()
    }

    fun fetchFavouriteRestaurants() {
        repository.fetchFavouriteRestaurants()
            .map {
                convertToList(groupByCuisine(it))
            }
            .subscribe(object : Observer<List<ListItem>> {

                override fun onNext(restaurants: List<ListItem>) {
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

}