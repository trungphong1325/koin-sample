package com.treeforcom.koin_sample.view.main.fragment.bookingmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.treeforcom.koin_sample.R
import com.treeforcom.koin_sample.model.request.bookingmanager.BookingManagerParam
import com.treeforcom.koin_sample.utils.DateHelper
import com.treeforcom.koin_sample.viewmodel.bookingmanager.BookingManagerViewModel
import kotlinx.android.synthetic.main.fragment_page_finished.*
import org.koin.android.viewmodel.ext.android.getViewModel

class FinishedPageFragment : Fragment() {
    private lateinit var mAdapter: FinishedPageAdapter
    private val viewModel: BookingManagerViewModel by lazy {
        getViewModel(BookingManagerViewModel::class)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_page_finished, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        initializeViewModel()
        viewModel.getBookingManager(BookingManagerParam(1, 1000, 0, DateHelper.getData()))
    }

    private fun initializeView() {
        context?.let {
            mAdapter = FinishedPageAdapter(it)
            listFinished.layoutManager = LinearLayoutManager(it)
            listFinished.adapter = mAdapter
        }
    }

    private fun initializeViewModel() {
        viewModel.bookingManager.observe(this, Observer {
            mAdapter.setData(it.data.data)
        })
        viewModel.showLoading.observe(this, Observer { showLoading ->
            when {
                showLoading -> {
                    swipeRefreshLayout.isRefreshing = true
                }
                else -> {
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        })

        viewModel.showError.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

}