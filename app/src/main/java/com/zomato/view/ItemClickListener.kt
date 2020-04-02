package com.zomato.view

interface ItemClickListener<T> {
    fun onItemClick(item: T)
    fun markFavourite(position: Int, item: T)
}