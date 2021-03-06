package com.zomato.view

import android.os.Bundle
import android.util.SparseArray
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.zomato.R
import com.zomato.RestaurantApplication
import com.zomato.SortEvent
import com.zomato.databinding.ActivityMainBinding
import com.zomato.view.baseview.BaseActivity
import com.zomato.view.fragment.FavRestaurantListFragment
import com.zomato.view.fragment.RestaurantListFragment
import com.zomato.viewmodel.RestaurantsViewModel
import com.zomato.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: RestaurantsViewModel

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {

        (application as RestaurantApplication).appComponent
            .inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(RestaurantsViewModel::class.java)

        mBinding.pager.apply {
            this.adapter = RestaurantPagerAdapter(supportFragmentManager)
        }
        mBinding.tabLayout.setupWithViewPager(mBinding.pager)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sort, menu);

        when (viewModel.sortEvent.value) {
            SortEvent.PRICE -> menu?.getItem(0)?.isChecked = true
            SortEvent.RATING -> menu?.getItem(1)?.isChecked = true
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        item.isChecked = true
        when (item.itemId) {
            R.id.sort_price -> viewModel.sortEvent.value = SortEvent.PRICE
            R.id.sort_rating -> viewModel.sortEvent.value = SortEvent.RATING
        }
        return super.onOptionsItemSelected(item)
    }

    class RestaurantPagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                1 -> FavRestaurantListFragment()
                else -> RestaurantListFragment()
            }
        }

        override fun getCount() = 2

        override fun getPageTitle(position: Int): CharSequence? {
            return if (position == 1) "Favourite" else "All"
        }

    }

}
