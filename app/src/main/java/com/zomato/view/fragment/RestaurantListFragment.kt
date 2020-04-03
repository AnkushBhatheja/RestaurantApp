package com.zomato.view.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zomato.R
import com.zomato.ZomatoApplication
import com.zomato.database.ListItem
import com.zomato.databinding.FragmentRestaurantListBinding
import com.zomato.model.Restaurant
import com.zomato.model.RestaurantData
import com.zomato.view.ItemClickListener
import com.zomato.view.adapter.RestaurantAdapter
import com.zomato.view.baseview.BaseFragment
import com.zomato.viewmodel.RestaurantsViewModel
import com.zomato.viewmodel.ViewModelFactory

import javax.inject.Inject


class RestaurantListFragment : BaseFragment<FragmentRestaurantListBinding, RestaurantsViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun layoutId(): Int {
        return R.layout.fragment_restaurant_list
    }

    override fun inject() {
        (activity?.application as ZomatoApplication).appComponent
            .inject(this)
    }

    override fun createViewModel(): RestaurantsViewModel {
        return ViewModelProvider(this, viewModelFactory)
            .get(RestaurantsViewModel::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {


        mBinding.viewModel = viewModel
        mBinding.recycleView.layoutManager = LinearLayoutManager(context)

        val adapter =
            RestaurantAdapter(object :
                ItemClickListener<ListItem> {
                override fun onItemClick(item: ListItem) {
                }

                override fun markFavourite(position: Int, item: ListItem) {
                }
            })
        mBinding.recycleView.adapter = adapter

        viewModel.fetchRestaurants()


        viewModel.restaurantsLiveData.observe(this, Observer {
            adapter.addAll(it)
        })


        if (viewModel.restaurantsLiveData.value != null) {
            adapter.addAll(viewModel.restaurantsLiveData.value)
        } else
            viewModel.search(viewModel.searchKey.value!!)

    }

}
