package com.zomato.view.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zomato.R
import com.zomato.RestaurantApplication
import com.zomato.databinding.FragmentFavRestaurantBinding
import com.zomato.view.adapter.RestaurantAdapter
import com.zomato.view.baseview.BaseFragment
import com.zomato.viewmodel.RestaurantsViewModel
import com.zomato.viewmodel.ViewModelFactory
import javax.inject.Inject


class FavRestaurantListFragment :
    BaseFragment<FragmentFavRestaurantBinding, RestaurantsViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun layoutId(): Int {
        return R.layout.fragment_fav_restaurant
    }

    override fun inject() {
        (activity?.application as RestaurantApplication).appComponent
            .inject(this)
    }

    override fun createViewModel(): RestaurantsViewModel {
        return activity?.viewModelStore?.let {
            ViewModelProvider(it, viewModelFactory)
                .get(RestaurantsViewModel::class.java)
        }!!
    }

    override fun initView(savedInstanceState: Bundle?) {

        mBinding.viewModel = viewModel
        mBinding.layoutRestaurant.recycleView.layoutManager = LinearLayoutManager(context)

        val adapter = RestaurantAdapter()
        mBinding.layoutRestaurant.recycleView.adapter = adapter

        viewModel.sortEvent.observe(this, Observer {
            viewModel.favouriteRestaurants.value =
                viewModel.sortRestaurant(viewModel.favouriteRestaurants.value)
        })

        viewModel.favouriteRestaurants.observe(this, Observer {
            adapter.addAll(viewModel.getDataToDisplay(it))
        })

        if (viewModel.favouriteRestaurants.value != null) {
            adapter.addAll(viewModel.getDataToDisplay(viewModel.favouriteRestaurants.value))
        } else
            viewModel.fetchFavouriteRestaurants()
    }
}
