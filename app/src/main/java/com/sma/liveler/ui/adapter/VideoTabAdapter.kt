package com.sma.liveler.ui.adapter

import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class VideoTabAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentList = ArrayList<Fragment>()
    private var fragmentName = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentName.add(title)
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentName.get(position)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }
}