package com.sma.liveler.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TabAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private var fragmentList = ArrayList<Fragment>()
    private var fragmentName = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position);
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentName.get(position)
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fragment: Fragment, name: String) {
        fragmentList.add(fragment)
        fragmentName.add(name)
    }
}