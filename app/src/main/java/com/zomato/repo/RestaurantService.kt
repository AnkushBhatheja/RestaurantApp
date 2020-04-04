package com.zomato.repo

import com.zomato.model.SearchResult
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RestaurantService {

    @GET("api/v2.1/search")
    fun fetchRestaurants(
        @Query("q") query: String?
    ): Single<SearchResult>

}