package com.zomato.view.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zomato.R
import com.zomato.RestaurantApplication
import com.zomato.database.ListItem
import com.zomato.databinding.FragmentRestaurantBinding
import com.zomato.model.RestaurantData
import com.zomato.view.ItemClickListener
import com.zomato.view.adapter.RestaurantAdapter
import com.zomato.view.baseview.BaseFragment
import com.zomato.viewmodel.RestaurantsViewModel
import com.zomato.viewmodel.ViewModelFactory

import javax.inject.Inject


class RestaurantListFragment : BaseFragment<FragmentRestaurantBinding, RestaurantsViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun layoutId(): Int {
        return R.layout.fragment_restaurant
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

        val adapter = RestaurantAdapter(true,
            object : ItemClickListener<ListItem> {
                override fun markFavourite(item: ListItem, isFavourite: Boolean) {
                    if (isFavourite) viewModel.addToFavourite(item as RestaurantData)
                    else viewModel.deleteFromFavourite(item as RestaurantData)
                }
            })


        mBinding.layoutRestaurant.recycleView.apply {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        viewModel.getAllRestaurants()

        viewModel.sortEvent.observe(this, Observer {
            viewModel.restaurantsLiveData.value =
                viewModel.sortRestaurant(viewModel.restaurantsLiveData.value)
        })

        viewModel.restaurantsLiveData.observe(this, Observer {
            adapter.addAll(viewModel.getDataToDisplay(it))
        })


        if (viewModel.restaurantsLiveData.value != null) {
            adapter.addAll(viewModel.getDataToDisplay(viewModel.restaurantsLiveData.value))
        } else
            viewModel.search(viewModel.searchKey.value!!)

    }

}
