package com.treeforcom.koin_sample.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.treeforcom.koin_sample.R
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    private lateinit var mPagerAdapter: HomePagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initializeView()
    }

    private fun initializeView() {
        mPagerAdapter = HomePagerAdapter(supportFragmentManager)
        viewpager.offscreenPageLimit = 1
        viewpager.adapter = mPagerAdapter
        tabItem.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_other -> viewpager.currentItem = 0
                R.id.nav_mess -> viewpager.currentItem = 1
                R.id.nav_booking -> viewpager.currentItem = 2
                R.id.nav_setting -> viewpager.currentItem = 3
            }
            false
        }
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) = Unit

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) = Unit

            override fun onPageSelected(position: Int) {
                tabItem.menu.getItem(position).isChecked = true
            }
        })
    }
}

