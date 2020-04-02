package com.zomato.view

import android.os.Bundle
import com.zomato.R
import com.zomato.databinding.ActivityMainBinding
import com.zomato.view.baseview.BaseActivity
import com.zomato.view.fragment.RestaurantListFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .add(
                    mBinding.container.id,
                    RestaurantListFragment()
                )
                .commit()
    }

}
