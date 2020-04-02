package com.zomato.viewmodel

import androidx.lifecycle.MutableLiveData
import com.zomato.R
import com.zomato.ZomatoApplication
import com.zomato.model.Restaurant
import com.zomato.model.RestaurantData
import com.zomato.repo.RestaurantRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
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

    val restaurantsLiveData: MutableLiveData<List<RestaurantData>> by lazy {
        MutableLiveData<List<RestaurantData>>()
    }


    fun search(key: String) {
        publishSubject.onNext(key)
    }

    private fun fetchRestaurants(query: String?) {
        repository.fetchRestaurants(query)
            .subscribe(object : SingleObserver<List<RestaurantData>> {
                override fun onSuccess(restaurants: List<RestaurantData>) {
                    mLoading.postValue(Pair(false, null))
                    restaurantsLiveData.postValue(restaurants)
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


    fun fetchRestaurants() {
        addDisposable(publishSubject.debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                fetchRestaurants(it)
            })
    }

}