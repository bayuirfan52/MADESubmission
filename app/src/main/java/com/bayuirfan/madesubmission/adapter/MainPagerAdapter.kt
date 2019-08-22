package com.bayuirfan.madesubmission.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import java.util.*

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragments = ArrayList<Fragment>()
    private val title = ArrayList<String>()

    fun add(fragment: Fragment, tabTitle: String) {
        fragments.add(fragment)
        title.add(tabTitle)
    }

    override fun getItem(i: Int): Fragment {
        return fragments[i]
    }

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }
}
