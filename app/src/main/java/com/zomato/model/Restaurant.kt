package com.zomato.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.zomato.database.Converters

@Entity
data class Restaurant(
    @TypeConverter(Converters::class) val highlights: List<String>,
    @PrimaryKey val id: Long,
    val name: String,
    val cuisines: String,
    val thumb: String,
    @SerializedName("user_rating") @Embedded val ratingData: RatingData,
    @SerializedName("average_cost_for_two") val avgPrice: Int
)

data class RatingData(
    @SerializedName("aggregate_rating") val avgRating: Float,
    @SerializedName("rating_obj") @Embedded val rating: Rating
)


data class Rating(
    @SerializedName("title") @Embedded val title: Title
)

data class Title(val text: String)
