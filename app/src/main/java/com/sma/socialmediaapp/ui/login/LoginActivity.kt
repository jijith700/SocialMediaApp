package com.sma.socialmediaapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.sma.socialmediaapp.R
import com.sma.socialmediaapp.databinding.ActivityLoginBinding
import com.sma.socialmediaapp.injection.ViewModelFactory
import com.sma.socialmediaapp.ui.home.HomeActivity
import com.sma.socialmediaapp.ui.registration.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(LoginViewModel::class.java)
        binding.viewModel = viewModel

        binding.btnLogin.setOnClickListener({

            if (TextUtils.isEmpty(binding.edtUserName.text)) {
                binding.edtUserName.error = "Invalid user name"

            } else if (TextUtils.isEmpty(binding.edtPassword.text)) {
                binding.edtPassword.error = "Invalid password"
            } else {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        })

        binding.tvSignUp.setOnClickListener({
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        })
    }
}