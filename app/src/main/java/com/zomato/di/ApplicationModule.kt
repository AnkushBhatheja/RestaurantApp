package com.zomato.di

import dagger.Module

@Module(includes = [NetworkModule::class,DBModule::class])
object ApplicationModule {


}