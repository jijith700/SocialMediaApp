package com.sma.liveler.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.sma.liveler.R
import com.sma.liveler.databinding.ActivityHomeBinding
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.about.AboutFragment
import com.sma.liveler.ui.account.BankAccountFragment
import com.sma.liveler.ui.adapter.TabAdapter
import com.sma.liveler.ui.adrequest.AdRequestFragment
import com.sma.liveler.ui.allusers.AllUsersFragment
import com.sma.liveler.ui.birthday.BirthdayFragment
import com.sma.liveler.ui.chatlist.ChatListFragment
import com.sma.liveler.ui.favfeeds.FavoriteFragment
import com.sma.liveler.ui.following.FollowingFragment
import com.sma.liveler.ui.friends.FriendsFragment
import com.sma.liveler.ui.groups.GroupsFragment
import com.sma.liveler.ui.login.LoginActivity
import com.sma.liveler.ui.notification.NotificationFragment
import com.sma.liveler.ui.pages.PagesFragment
import com.sma.liveler.ui.timeline.PostFragment
import com.sma.liveler.ui.videos.VideoFragment
import com.sma.liveler.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nav_header_main.*
import timber.log.Timber


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var tabAdapter: TabAdapter

    var userId: Int? = null

    private var allUsersFragment: AllUsersFragment? = null

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: HomeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(
                    this@HomeActivity,
                    PostRepository(this@HomeActivity)
                ) as T
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

        switchPage(PostFragment(), false)
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
                        switchPage(PostFragment(), false)
                    }
                    2 -> {
                        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            binding.drawerLayout.closeDrawers()
                        }
                        switchPage(FollowingFragment(), false)
                    }
                    3 -> {
                        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            binding.drawerLayout.closeDrawers()
                        }
                        switchPage(ChatListFragment(), false)
                    }
                    4 -> {
                        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            binding.drawerLayout.closeDrawers()
                        }
                        switchPage(NotificationFragment(), false)
                    }
                    5 -> {
                        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            binding.drawerLayout.closeDrawers()
                        }
                        layoutSearch.visibility = View.VISIBLE
                        if (allUsersFragment != null) {
                            switchPage(allUsersFragment!!, false)
                        } else {
                            allUsersFragment = AllUsersFragment()
                            switchPage(allUsersFragment!!, false)
                        }
                    }
                    6 -> {
                        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            binding.drawerLayout.closeDrawers()
                        }
                        switchPage(ChatListFragment(), false)
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
                        switchPage(PostFragment(), false)
                    }
                    2 -> {
                        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            binding.drawerLayout.closeDrawers()
                        }
                        switchPage(FollowingFragment(), false)
                    }
                    3 -> {
                        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            binding.drawerLayout.closeDrawers()
                        }
                        switchPage(ChatListFragment(), false)
                    }
                    4 -> {
                        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            binding.drawerLayout.closeDrawers()
                        }
                        switchPage(NotificationFragment(), false)
                    }
                    5 -> {
                        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            binding.drawerLayout.closeDrawers()
                        }
                        layoutSearch.visibility = View.VISIBLE
                        if (allUsersFragment != null) {
                            switchPage(allUsersFragment!!, false)
                        } else {
                            allUsersFragment = AllUsersFragment()
                            switchPage(allUsersFragment!!, false)
                        }
                    }
                    6 -> {
                        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            binding.drawerLayout.closeDrawers()
                        }
                        switchPage(ChatListFragment(), false)
                    }
                }
            }
        })

        val headerView = binding.navView.getHeaderView(0)
        val tvViewProfile = headerView.findViewById<TextView>(R.id.tvViewProfile)
        tvViewProfile.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawers()
                }
                switchPage(AboutFragment(), false)
            }
        })


        viewModel.getNotifications()

        viewModel.user.observe(this, Observer {
            Picasso.get().load(it.profile?.profile_picture).placeholder(R.drawable.ic_user_avtar)
                .into(ivProfilePic)
            tvUserName.text = it.firstName
            userId = it.id
        })

        ibClose.setOnClickListener {
            layoutSearch.visibility = View.GONE
            edtSearch.setText("")
            switchPage(PostFragment(), false)
        }

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                allUsersFragment?.search(p0.toString())
            }
        })
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_timeline -> switchPage(PostFragment(), false)
            R.id.nav_video -> switchPage(VideoFragment(), false)
            R.id.nav_friends -> switchPage(FriendsFragment(), false)
            R.id.nav_bank -> switchPage(BankAccountFragment(), false)
            R.id.nav_adrequest -> switchPage(AdRequestFragment(), false)
            R.id.nav_favorites -> switchPage(FavoriteFragment(), false)
            R.id.nav_groups -> switchPage(GroupsFragment(), false)
            R.id.nav_pages -> switchPage(PagesFragment(), false)
            R.id.nav_birthdays -> switchPage(BirthdayFragment(), false)
            R.id.nav_logout -> {
                Utils.clearAllPreference(this)
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
        Timber.d("addToBackStack = %s", addToBackStack)
        Timber.i("Fragment Name: %s", fragment.javaClass.getSimpleName())

        if (fragment == null) {
            Timber.e("< fragment is null")
            return
        }

        if (!TextUtils.isEmpty(fragment.javaClass.getSimpleName())) {
            val oldFragment = supportFragmentManager
                .findFragmentByTag(fragment.javaClass.getSimpleName())

            if (oldFragment != null && oldFragment.isAdded()) {
                supportFragmentManager.beginTransaction().remove(oldFragment)
                    .commitAllowingStateLoss()
            }
        } else {
            Timber.e("< fragment name is null or empty")
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
            Timber.i("Fragment_Name: %s", fragment.javaClass.getSimpleName())
        }

        try {
            transaction.commitAllowingStateLoss()
        } catch (e: IllegalStateException) {
            Timber.e("fragment already added: %s", e.toString())
        }

        Utils.hideKeyboard(this, tabLayout)
    }
}
