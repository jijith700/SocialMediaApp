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

        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.rb_tab1))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.rb_tab2))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.rb_tab3))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.rb_tab4))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.rb_tab5))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.rb_tab6))
        binding.tabLayout.tabSelectedIndicator
    }
}
