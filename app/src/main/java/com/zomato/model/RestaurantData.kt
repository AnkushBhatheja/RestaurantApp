package com.zomato.model

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.zomato.database.ListItem

@Entity(primaryKeys = ["id"])
data class RestaurantData(
    @Embedded val restaurant: Restaurant
) : ListItem {
    override fun getType() = ListItem.Type.RESTAURANT

    override fun equals(other: Any?): Boolean {
        return restaurant.id == (other as RestaurantData).restaurant.id
    }

    override fun hashCode(): Int {
        return restaurant.hashCode()
    }

}

data class Restaurant(
    val id: Long,
    val name: String,
    val cuisines: String,
    val thumb: String,
    val currency: String,
    var isFavourite: Boolean,
    @SerializedName("location") @Embedded val address: Address,
    @SerializedName("user_rating") @Embedded val ratingData: RatingData,
    @SerializedName("average_cost_for_two") val avgPrice: Int
)

data class Address(@SerializedName("locality_verbose") val value: String)

data class RatingData(
    @SerializedName("aggregate_rating") val avgRating: Double,
    @SerializedName("rating_color") val color: String,
    @SerializedName("rating_obj") @Embedded val rating: Rating
)


data class Rating(
    @SerializedName("title") @Embedded val title: Title
)

data class Title(val text: String)
