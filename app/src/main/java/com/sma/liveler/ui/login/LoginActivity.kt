package com.sma.liveler.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.sma.liveler.R
import com.sma.liveler.databinding.ActivityLoginBinding
import com.sma.liveler.repository.UserRepository
import com.sma.liveler.ui.home.HomeActivity
import com.sma.liveler.ui.registration.RegisterActivity
import com.sma.liveler.utils.Utils

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var errorSnackbar: Snackbar? = null


    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: LoginViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(
                    this@LoginActivity,
                    UserRepository(this@LoginActivity)
                ) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel

        viewModel.errorMessage.observe(this, Observer {
            Utils.alert(this, it)
        })
        viewModel.loadingVisibility.observe(this, Observer {
            binding.layoutLoading.visibility = it
        })
        viewModel.success.observe(this, Observer {
            if (it) {
                val homeIntent = Intent(this, HomeActivity::class.java)
                startActivity(homeIntent)
                finish()
            }
        })

        binding.btnLogin.setOnClickListener {
            viewModel.onclickLogin(
                binding.edtUserName.text.toString(),
                binding.edtPassword.text.toString()
            )
        }

        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}