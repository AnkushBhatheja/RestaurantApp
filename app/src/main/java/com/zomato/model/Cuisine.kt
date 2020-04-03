package com.zomato.model

import com.zomato.database.ListItem


data class Cuisine(val name: String) : ListItem {
    override fun getType() = ListItem.Type.CUISINE
}