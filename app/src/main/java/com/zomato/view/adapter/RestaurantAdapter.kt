package com.zomato.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zomato.R
import com.zomato.database.ListItem
import com.zomato.databinding.ItemCuisineBinding
import com.zomato.databinding.ItemRestaurantBinding
import com.zomato.model.Cuisine

import com.zomato.model.Restaurant
import com.zomato.model.RestaurantData
import com.zomato.view.ItemClickListener
import kotlinx.android.synthetic.main.item_cuisine.view.*

class RestaurantAdapter constructor(
    private val enableFavourite: Boolean = false,
    private val listener: ItemClickListener<ListItem>? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: MutableList<ListItem> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return items[position].getType().ordinal
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        if (viewType == ListItem.Type.CUISINE.ordinal) {
            val binding: ItemCuisineBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_cuisine, parent, false
            );
            return CuisineHolder(binding)
        }

        val binding: ItemRestaurantBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_restaurant, parent, false
        );
        return RestaurantHolder(binding)

    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is CuisineHolder) {
            holder.bind(items[position])
        } else if (holder is RestaurantHolder) {
            holder.bind(items[position], listener)
            holder.mBinding.checkbox.isEnabled = enableFavourite
        }
    }

    fun addAll(list: List<ListItem>?) {
        items.clear()
        items.addAll(list ?: mutableListOf())
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    inner class RestaurantHolder(val mBinding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(item: ListItem, listener: ItemClickListener<ListItem>?) {
            mBinding.restaurant = (item as RestaurantData).restaurant
            mBinding.checkbox.setOnCheckedChangeListener { _, b ->
                item.restaurant.isFavourite = b
                listener?.markFavourite(item, b)
            }
        }
    }

    inner class CuisineHolder(val mBinding: ItemCuisineBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        fun bind(item: ListItem) {
            mBinding.root.tvCuisine.text = (item as Cuisine).name
        }
    }
}