package com.bayuirfan.madesubmission.adapter

import androidx.fragment.app.*
import java.util.*

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

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
