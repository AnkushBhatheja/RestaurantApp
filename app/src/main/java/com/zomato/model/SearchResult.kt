package com.zomato.model

import com.google.gson.annotations.SerializedName


data class SearchResult(
    @SerializedName("restaurants") val restaurants: List<RestaurantData>
)