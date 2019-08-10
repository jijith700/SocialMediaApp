package com.sma.socialmediaapp.ui.home

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sma.socialmediaapp.R
import com.sma.socialmediaapp.databinding.ActivityHomeBinding
import com.sma.socialmediaapp.injection.ViewModelFactory
import com.sma.socialmediaapp.ui.adapter.TabAdapter
import com.sma.socialmediaapp.ui.gallery.GalleryFragment
import com.sma.socialmediaapp.ui.profile.ProfileFragment
import com.sma.socialmediaapp.ui.timeline.TimelineFragment
import com.sma.socialmediaapp.ui.videochannel.VideoChannelFragment


class HomeActivity : AppCompatActivity() {

    var TAG = HomeActivity::class.java.simpleName

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var tabAdapter: TabAdapter

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

        switchPage(TimelineFragment(), false)
        switchPage(VideoChannelFragment(), true)
        switchPage(GalleryFragment(), true)
        switchPage(ProfileFragment(), true)


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


    /**
     * This method used for controlling the navigation throughout the application
     *
     * @param fragment The fragment to be displayed
     * @param addToBackStack The flag used determine addition of fragment to back stack
     */
    fun switchPage(fragment: Fragment, addToBackStack: Boolean) {
        Log.d(
            TAG, "addToBackStack = [" + addToBackStack + "]"
        )

        if (fragment == null) {
            Log.e(TAG, "< fragment is null")
            return
        }

        if (!TextUtils.isEmpty(fragment.javaClass.getSimpleName())) {
            val oldFragment = supportFragmentManager
                .findFragmentByTag(fragment.javaClass.getSimpleName())

            if (oldFragment != null && oldFragment.isAdded()) {
                supportFragmentManager.beginTransaction().remove(oldFragment).commitAllowingStateLoss()
            }
        } else {
            Log.e(TAG, "< fragment name is null or empty")
        }

        val transaction = supportFragmentManager.beginTransaction()

        /*transaction.setCustomAnimations(
                      com.android.internal.R.animator.custom_fade_in,
                       com.android.internal.R.animator.custom_fade_out,
                       com.android.internal.R.animator.custom_fade_in,
                       com.android.internal.R.animator.custom_fade_out);*/

        //       transaction.replace(R.id.main_content, fragment);
        transaction.replace(R.id.fragmentContainer, fragment, fragment.javaClass.getSimpleName())

        if (addToBackStack) {
            transaction.addToBackStack(fragment.javaClass.getSimpleName())
            Log.i(TAG, "Fragment_Name: " + fragment.javaClass.getSimpleName())
        }

        try {
            transaction.commitAllowingStateLoss()
        } catch (e: IllegalStateException) {
            Log.e(TAG, "fragment already added: " + e.toString())
        }

    }
}
