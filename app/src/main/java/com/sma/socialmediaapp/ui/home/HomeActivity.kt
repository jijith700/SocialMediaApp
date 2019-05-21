package com.sma.socialmediaapp.ui.home

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.sma.socialmediaapp.R
import com.sma.socialmediaapp.databinding.ActivityHomeBinding
import com.sma.socialmediaapp.injection.ViewModelFactory
import android.widget.LinearLayout






class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(HomeViewModel::class.java)
        binding.viewModel = viewModel


        val tabView1 = View.inflate(this, R.layout.layout_tab, null)
//        tabView1.setBackgroundColor(resources.getColor(R.color.colorBgTab1Normal, null))
        tabView1.setBackgroundResource(R.drawable.rb_tab1)
//        tabView1.findViewById<ImageView>(R.id.icon).setBackgroundResource(R.drawable.rb_tab1)
        binding.tabLayout.addTab(binding.tabLayout.newTab().setCustomView(tabView1))


        /*binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.rb_tab1))*/
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.rb_tab2))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.rb_tab3))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.rb_tab4))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.rb_tab5))

        val tabView6 = View.inflate(this, R.layout.layout_tab, null)
        tabView6.setBackgroundResource(R.drawable.rb_tab6)
        binding.tabLayout.addTab(binding.tabLayout.newTab().setCustomView(tabView6))

        /* binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.rb_tab6))*/
        binding.tabLayout.tabSelectedIndicator

        val tabsContainer = binding.tabLayout.getChildAt(0) as LinearLayout

        val childLayout1 = tabsContainer.getChildAt(0) as LinearLayout
        val childLayout2 = tabsContainer.getChildAt(5) as LinearLayout

        var tabViewColor1 = childLayout1.getChildAt(0).parent as LinearLayout
        tabViewColor1.setBackgroundColor(resources.getColor(R.color.colorBgTab1Normal, null))

        var tabViewColor6 = childLayout2.getChildAt(0).parent as LinearLayout
        tabViewColor6.setBackgroundColor(resources.getColor(R.color.colorBgTab6Normal, null))


//        val mLocalActivityManager = LocalActivityManager(this, false)
//        mLocalActivityManager.dispatchCreate(savedInstanceState);
//
//        binding.thTabs.setup(mLocalActivityManager)
//
//        binding.thTabs.
//
//        var tab1 = binding.thTabs.newTabSpec("A")
//        tab1.setIndicator("A", resources.getDrawable(R.drawable.rb_tab1, null))
//        tab1.setContent(Intent(this, LoginActivity::class.java))
//
//        var tab2 = binding.thTabs.newTabSpec("b")
//        tab2.setIndicator("A", resources.getDrawable(R.drawable.rb_tab2, null))
//        tab2.setContent(Intent(this, LoginActivity::class.java))
//
//        var tab3 = binding.thTabs.newTabSpec("c")
//        tab3.setIndicator("A", resources.getDrawable(R.drawable.rb_tab3, null))
//        tab3.setContent(Intent(this, LoginActivity::class.java))
//
//        var tab4 = binding.thTabs.newTabSpec("d")
//        tab4.setIndicator("A", resources.getDrawable(R.drawable.rb_tab4, null))
//        tab4.setContent(Intent(this, LoginActivity::class.java))
//
//        binding.thTabs.addTab(tab1)
//        binding.thTabs.addTab(tab2)
//        binding.thTabs.addTab(tab3)
//        binding.thTabs.addTab(tab4)

    }
}
