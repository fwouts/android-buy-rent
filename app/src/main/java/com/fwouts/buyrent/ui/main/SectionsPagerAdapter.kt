package com.fwouts.buyrent.ui.main

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fwouts.buyrent.R
import com.fwouts.buyrent.ui.list.PropertyListFragment

private val TAB_TITLES = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2
)

class SectionsPagerAdapter(fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    @StringRes fun getTabTitle(position: Int): Int {
        return TAB_TITLES[position]
    }

    override fun createFragment(position: Int): Fragment {
        return PropertyListFragment.newInstance(position + 1)
    }
}