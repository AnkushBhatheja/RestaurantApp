package com.zomato.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zomato.viewmodel.RestaurantsViewModel
import com.zomato.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RestaurantsViewModel::class)
    abstract fun bindFavRestaurantsViewModel(favRestaurantsViewModel: RestaurantsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}