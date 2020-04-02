package com.zomato.di

import com.zomato.repo.RestaurantRepository
import com.zomato.repo.RestaurantRepositoryImpl
import dagger.Binds
import dagger.Module

@Module(includes = [ViewModelModule::class])
abstract class RestaurantModule {
    @Binds
    abstract fun bindRestaurantRepository(restaurantRepository : RestaurantRepositoryImpl): RestaurantRepository

}