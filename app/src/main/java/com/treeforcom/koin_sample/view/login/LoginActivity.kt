package com.treeforcom.koin_sample.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.treeforcom.koin_sample.R
import com.treeforcom.koin_sample.model.pref.UserManager
import com.treeforcom.koin_sample.model.request.login.LoginEmaiParam
import com.treeforcom.koin_sample.model.response.login.UserResponse
import com.treeforcom.koin_sample.view.main.HomeActivity
import com.treeforcom.koin_sample.viewmodel.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.getViewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by lazy {
        getViewModel(LoginViewModel::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        when {
            UserManager.getToken(this)?.isNotEmpty() == true -> {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }
        initViewModel()
        login.setOnClickListener {
            viewModel.authenticate(
                LoginEmaiParam(
                    email.text.toString(),
                    password.text.toString(),
                    0,
                    "ANDROID"
                )
            )
        }
    }

    private fun initViewModel() {
        viewModel.userModel.observe(this, Observer {
            handleWhenLoginSuccess(it)
        })
        viewModel.showLoading.observe(this, Observer { showLoading ->
            mainProgressBar.visibility = if (showLoading) View.VISIBLE else View.GONE
        })
        viewModel.showError.observe(this, Observer { showError ->
            Toast.makeText(this, showError, Toast.LENGTH_SHORT).show()
        })
    }

    private fun handleWhenLoginSuccess(model: UserResponse?) {
        UserManager.saveToken(this, model?.data?.login_token ?: "")
        startActivity(Intent(this, VerifyPhoneActivity::class.java))
        finish()
    }
}
