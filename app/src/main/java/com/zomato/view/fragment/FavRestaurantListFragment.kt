package com.zomato.view.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zomato.R
import com.zomato.ZomatoApplication
import com.zomato.databinding.FragmentFavRestaurantBinding
import com.zomato.view.adapter.RestaurantAdapter
import com.zomato.view.baseview.BaseFragment
import com.zomato.viewmodel.FavRestaurantsViewModel
import com.zomato.viewmodel.ViewModelFactory
import javax.inject.Inject


class FavRestaurantListFragment :
    BaseFragment<FragmentFavRestaurantBinding, FavRestaurantsViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun layoutId(): Int {
        return R.layout.fragment_fav_restaurant
    }

    override fun inject() {
        (activity?.application as ZomatoApplication).appComponent
            .inject(this)
    }

    override fun createViewModel(): FavRestaurantsViewModel {
        return ViewModelProvider(this, viewModelFactory)
            .get(FavRestaurantsViewModel::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {

        activity?.actionBar?.title = getString(R.string.favourite_restaurants)

        mBinding.viewModel = viewModel
        mBinding.layoutRestaurant.recycleView.layoutManager = LinearLayoutManager(context)

        val adapter = RestaurantAdapter()
        mBinding.layoutRestaurant.recycleView.adapter = adapter


        viewModel.favouriteRestaurants.observe(this, Observer {
            adapter.addAll(it)
        })

        if (viewModel.favouriteRestaurants.value != null) {
            adapter.addAll(viewModel.favouriteRestaurants.value)
        } else
            viewModel.fetchFavouriteRestaurants()
    }
}
