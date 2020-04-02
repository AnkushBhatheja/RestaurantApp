package com.zomato.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zomato.ZomatoApplication
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel(application: ZomatoApplication) : AndroidViewModel(application) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val mLoading: MutableLiveData<Pair<Boolean, String?>> = MutableLiveData()

    val mShowMessage: MutableLiveData<String> = MutableLiveData()


    protected fun addDisposable(observable: Disposable) {
        compositeDisposable.add(observable)
    }


}