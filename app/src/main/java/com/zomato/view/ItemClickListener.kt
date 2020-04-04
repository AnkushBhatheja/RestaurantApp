package com.zomato.view

interface ItemClickListener<T> {
    fun markFavourite(item: T, boolean: Boolean)
}