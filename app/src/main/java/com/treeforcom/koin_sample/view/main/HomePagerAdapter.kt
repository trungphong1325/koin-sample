package com.treeforcom.koin_sample.view.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.treeforcom.koin_sample.view.main.fragment.bookingmanager.BookingPageFragment
import com.treeforcom.koin_sample.view.main.fragment.ChatPageFragment
import com.treeforcom.koin_sample.view.main.fragment.ListUserPageFragment
import com.treeforcom.koin_sample.view.main.fragment.SettingPageFragment

class HomePagerAdapter( fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ListUserPageFragment()
            1 -> ChatPageFragment()
            2 -> BookingPageFragment()
            else -> SettingPageFragment()
        }
    }

}