package com.sma.socialmediaapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.sma.socialmediaapp.R
import com.sma.socialmediaapp.databinding.ActivityHomeBinding
import com.sma.socialmediaapp.databinding.ActivityLoginBinding
import com.sma.socialmediaapp.injection.ViewModelFactory
import com.sma.socialmediaapp.ui.login.LoginViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(HomeViewModel::class.java)
        binding.viewModel = viewModel

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Feed"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Profile"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Chat"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Search"));
    }
}
