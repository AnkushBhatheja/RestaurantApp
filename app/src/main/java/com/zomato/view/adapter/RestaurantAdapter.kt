package com.zomato.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zomato.R
import com.zomato.databinding.ItemRestaurantBinding

import com.zomato.model.Restaurant
import com.zomato.model.RestaurantData
import com.zomato.view.ItemClickListener

class RestaurantAdapter constructor(private val listener: ItemClickListener<RestaurantData>) :
    RecyclerView.Adapter<RestaurantAdapter.ItemHolder>() {

    private val restaurants: MutableList<RestaurantData> = mutableListOf()

//    override fun getItemViewType(position: Int): Int {
//        return if (position == authors.size - 1 && authors[position].id < 0) 0 else 1
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantAdapter.ItemHolder {

//        if (viewType == 0) {
//            val binding: ItemFooterBinding = DataBindingUtil.inflate(
//                LayoutInflater.from(parent.context),
//                R.layout.item_footer, parent, false
//            );
//            return FooterHolder(binding)
//        }
        val binding: ItemRestaurantBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_restaurant, parent, false
        );
        return ItemHolder(binding)
    }


    override fun getItemCount(): Int {
        return restaurants.size
    }

    override fun onBindViewHolder(holder: RestaurantAdapter.ItemHolder, position: Int) {

//        if (holder is ItemHolder) {
//            holder.mBinding.author = restaurants[position]
//            holder.bind(restaurants[position], listener)
//        } else if (holder is FooterHolder) {
//            holder.itemView.setOnClickListener {
//                listener.showMore()
//            }
        holder.mBinding.restaurant = restaurants[position].restaurant
        holder.bind(restaurants[position], listener)
    }

    fun addAll(items: List<RestaurantData>?) {
        restaurants.clear()
        restaurants.addAll(items ?: mutableListOf())
        notifyDataSetChanged()
    }

    fun clear() {
        restaurants.clear()
        notifyDataSetChanged()
    }

    class ItemHolder(val mBinding: ItemRestaurantBinding) : RecyclerView.ViewHolder(mBinding.root) {

        fun bind(item: RestaurantData, listener: ItemClickListener<RestaurantData>) {
            mBinding.root.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }

//    class FooterHolder(mBinding: ItemFooterBinding) :
//        RecyclerView.ViewHolder(mBinding.root) {
//
//    }
}