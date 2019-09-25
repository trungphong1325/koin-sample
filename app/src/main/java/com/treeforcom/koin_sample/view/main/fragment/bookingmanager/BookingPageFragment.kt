package com.treeforcom.koin_sample.view.main.fragment.bookingmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.treeforcom.koin_sample.R
import kotlinx.android.synthetic.main.fragment_page_booking.*

class BookingPageFragment : Fragment(){
    private lateinit var mAdapter: BookingPageAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_page_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }

    private fun initializeView() {
            mAdapter = BookingPageAdapter(childFragmentManager)
            viewPagerBooking.adapter = mAdapter
            tabBooking.setupWithViewPager(viewPagerBooking)
    }
}