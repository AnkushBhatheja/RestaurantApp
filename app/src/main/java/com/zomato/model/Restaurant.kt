package com.zomato.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.zomato.database.Converters
import com.zomato.database.ListItem

@Entity
data class Restaurant(
    @PrimaryKey val id: Long,
    val name: String,
    val cuisines: String,
    val thumb: String,
    val currency: String,
    @SerializedName("location") @Embedded val address: Address,
    @SerializedName("user_rating") @Embedded val ratingData: RatingData,
    @SerializedName("average_cost_for_two") val avgPrice: Int
) : ListItem {
    override fun getType() = ListItem.Type.RESTAURANT

}

data class Address(@SerializedName("locality_verbose") val value: String)

data class RatingData(
    @SerializedName("aggregate_rating") val avgRating: Float,
    @SerializedName("rating_color") val color: String,
    @SerializedName("rating_obj") @Embedded val rating: Rating
)


data class Rating(
    @SerializedName("title") @Embedded val title: Title
)

data class Title(val text: String)
