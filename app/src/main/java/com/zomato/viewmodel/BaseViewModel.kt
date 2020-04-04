package com.zomato.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zomato.RestaurantApplication
import com.zomato.SortEvent
import com.zomato.database.ListItem
import com.zomato.model.Cuisine
import com.zomato.model.RestaurantData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlin.math.ceil

open class BaseViewModel(application: RestaurantApplication) : AndroidViewModel(application) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val mLoading: MutableLiveData<Pair<Boolean, String?>> = MutableLiveData()

    val mShowMessage: MutableLiveData<String> = MutableLiveData()


    protected fun addDisposable(observable: Disposable) {
        compositeDisposable.add(observable)
    }

}