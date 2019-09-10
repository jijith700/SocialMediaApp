package com.sma.liveler.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.sma.liveler.R
import com.sma.liveler.databinding.ActivityHomeBinding
import com.sma.liveler.ui.about.AboutFragment
import com.sma.liveler.ui.adapter.TabAdapter
import com.sma.liveler.ui.favfeeds.FavoriteFragment
import com.sma.liveler.ui.friends.FriendsFragment
import com.sma.liveler.ui.groups.GroupsFragment
import com.sma.liveler.ui.login.LoginActivity
import com.sma.liveler.ui.pages.PagesFragment
import com.sma.liveler.ui.timeline.TimelineFragment


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var TAG = HomeActivity::class.java.simpleName

    private lateinit var binding: ActivityHomeBinding
    private lateinit var tabAdapter: TabAdapter

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: HomeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(this@HomeActivity) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewModel = viewModel


        val tabView1 = View.inflate(this, R.layout.layout_tab, null)
//        tabView1.setBackgroundColor(resources.getColor(R.color.colorBgTab1Normal, null))
        tabView1.setBackgroundResource(R.drawable.rb_tab_menu)
//        tabView1.findViewById<ImageView>(R.id.icon).setBackgroundResource(R.drawable.rb_tab_menu)
        binding.tabLayout.addTab(binding.tabLayout.newTab().setCustomView(tabView1))


        /*binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.rb_tab_menu))*/
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.rb_tab_home))
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
        val childLayout2 = tabsContainer.getChildAt(6) as LinearLayout

        var tabViewColor1 = childLayout1.getChildAt(0).parent as LinearLayout
        tabViewColor1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBgTab1Normal))

        var tabViewColor6 = childLayout2.getChildAt(0).parent as LinearLayout
        tabViewColor6.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBgTab6Normal))

        switchPage(TimelineFragment(), false)
        /*switchPage(VideoChannelFragment(), true)
        switchPage(GalleryFragment(), true)*/


//        val mLocalActivityManager = LocalActivityManager(this, false)
//        mLocalActivityManager.dispatchCreate(savedInstanceState);
//
//        binding.thTabs.setup(mLocalActivityManager)
//
//        binding.thTabs.
//
//        var tab1 = binding.thTabs.newTabSpec("A")
//        tab1.setIndicator("A", resources.getDrawable(R.drawable.rb_tab_menu, null))
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

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this);

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabReselected(tab: TabLayout.Tab?) {
                if (tab!!.position == 0) {
                    if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.openDrawer(GravityCompat.START)
                    } else {
                        binding.drawerLayout.closeDrawers()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            binding.drawerLayout.openDrawer(GravityCompat.START)
                        } else {
                            binding.drawerLayout.closeDrawers()
                        }
                    }
                    1 -> {
                        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            binding.drawerLayout.closeDrawers()
                        }
                        switchPage(TimelineFragment(), false)
                    }
                }
            }
        })

        val headerView =  binding.navView.getHeaderView(0)
        val tvViewProfile = headerView.findViewById<TextView>(R.id.tvViewProfile)
        tvViewProfile.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawers()
                }
                switchPage(AboutFragment(), false)
            }
        })
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_timeline -> switchPage(TimelineFragment(), false)
            R.id.nav_friends -> switchPage(FriendsFragment(), false)
            R.id.nav_favorites -> switchPage(FavoriteFragment(), false)
            R.id.nav_groups -> switchPage(GroupsFragment(), false)
            R.id.nav_pages -> switchPage(PagesFragment(), false)
            R.id.nav_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.drawerLayout.closeDrawers()
        return true;
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
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
