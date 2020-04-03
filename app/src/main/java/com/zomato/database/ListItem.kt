package com.zomato.database

interface ListItem {

    enum class Type {
        CUISINE, RESTAURANT
    }

    fun getType(): Type
}