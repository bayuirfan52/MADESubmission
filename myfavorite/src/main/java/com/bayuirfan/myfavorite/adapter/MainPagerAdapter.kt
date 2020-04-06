package com.bayuirfan.myfavorite.adapter

import androidx.fragment.app.*

class MainPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments = ArrayList<Fragment>()
    private val title = ArrayList<String>()

    fun add(fragment: Fragment, tabTitle: String) {
        fragments.add(fragment)
        title.add(tabTitle)
    }

    override fun getItem(p0: Int): Fragment = fragments[p0]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = title[position]
}