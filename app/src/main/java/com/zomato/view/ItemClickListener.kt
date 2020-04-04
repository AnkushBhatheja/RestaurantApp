package com.zomato.view

interface ItemClickListener<T> {
    fun markFavourite(item: T, isFavourite: Boolean)
}