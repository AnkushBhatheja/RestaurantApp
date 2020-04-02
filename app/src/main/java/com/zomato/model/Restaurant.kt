package com.zomato.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Restaurant(
    @PrimaryKey val id: Long,
    val name: String,
    val cuisines: String,
    val thumb: String,
//    val highlights: List<String>,
    @SerializedName("average_cost_for_two") val avgPrice: Int
)