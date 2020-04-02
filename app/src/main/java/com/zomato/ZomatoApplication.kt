package com.zomato

import android.app.Application
import com.zomato.di.ApplicationComponent
import com.zomato.di.DaggerApplicationComponent

class ZomatoApplication : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.builder()
            .application(this)
            .build();
    }
}