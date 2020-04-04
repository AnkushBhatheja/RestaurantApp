package com.zomato.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.zomato.R
import com.zomato.databinding.ActivityMainBinding
import com.zomato.view.baseview.BaseActivity
import com.zomato.view.fragment.FavRestaurantListFragment
import com.zomato.view.fragment.RestaurantListFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {

//        if (savedInstanceState == null)
//            supportFragmentManager.beginTransaction()
//                .add(
//                    mBinding.container.id,
//                    RestaurantListFragment()
//                )
//                .commit()

        mBinding.pager.apply {
            this.adapter = RestaurantPagerAdapter(supportFragmentManager)
        }
        mBinding.tabLayout.setupWithViewPager(mBinding.pager)
    }


    class RestaurantPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(
        fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
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
