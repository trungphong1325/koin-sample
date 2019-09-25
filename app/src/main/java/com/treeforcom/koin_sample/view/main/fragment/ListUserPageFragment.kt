package com.treeforcom.koin_sample.view.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.widget.textChanges
import com.treeforcom.koin_sample.R
import com.treeforcom.koin_sample.model.request.listuser.GetListUserParam
import com.treeforcom.koin_sample.viewmodel.home.HomeViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_page_list_user.*
import kotlinx.android.synthetic.main.fragment_page_list_user.view.*
import org.koin.android.viewmodel.ext.android.getViewModel
import java.util.concurrent.TimeUnit

class ListUserPageFragment : Fragment() {
    private lateinit var mAdapter: TrainerTraineeAdapter
    private val compositeDisposable = CompositeDisposable()
    private val viewModel: HomeViewModel by lazy {
        getViewModel(HomeViewModel::class)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = LayoutInflater.from(context)
            .inflate(R.layout.fragment_page_list_user, container, false)
        initializeView(rootView)
        initializeListener(rootView)
        return rootView
    }

    private fun initializeListener(rootView: View?) {
        compositeDisposable.addAll(
            rootView?.search?.textChanges()?.debounce(500, TimeUnit.MILLISECONDS)?.subscribe {
                getListTrainerOrTrainee(
                    when {
                        it.isNotEmpty() -> it.toString()
                        else -> null
                    }
                )
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModel()
    }

    private fun getListTrainerOrTrainee(nickName: String? = null) {
        viewModel.getListTrainerOrTrainee(
            GetListUserParam(
                1,
                1000,
                10.7776897,
                106.6660054,
                2,
                nickName,
                18,
                46
            )
        )
    }

    private fun initializeView(rootView: View?) {
        context?.let {
            mAdapter = TrainerTraineeAdapter(it)
            rootView?.listUser?.layoutManager = LinearLayoutManager(it)
            rootView?.listUser?.adapter = mAdapter
        }
        rootView?.swipeRefreshLayout?.setOnRefreshListener {
            getListTrainerOrTrainee()
        }
    }

    private fun initializeViewModel() {
        viewModel.trainerTraineeModels.observe(this, Observer {
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}