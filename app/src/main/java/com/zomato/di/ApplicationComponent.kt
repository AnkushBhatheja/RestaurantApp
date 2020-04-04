package com.zomato.di

import com.zomato.ZomatoApplication
import com.zomato.view.fragment.FavRestaurantListFragment
import com.zomato.view.fragment.RestaurantListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RestaurantModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: ZomatoApplication): Builder

        fun build(): ApplicationComponent

    }

    fun inject(restaurantListFragment: RestaurantListFragment)
    fun inject(favRestaurantListFragment: FavRestaurantListFragment)

}