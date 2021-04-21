package com.fwouts.buyrent.ui.main

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fwouts.buyrent.R
import com.fwouts.buyrent.repositories.ListType
import com.fwouts.buyrent.ui.list.PropertyListFragment

class SectionsPagerAdapter(fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    @StringRes fun getTabTitle(position: Int): Int {
        return if (position == 0) {
            R.string.tab_buy
        } else {
            R.string.tab_rent
        }
    }

    override fun createFragment(position: Int): Fragment {
        return PropertyListFragment.newInstance(
            if (position == 0) {
                ListType.BUY
            } else {
                ListType.RENT
            }
        )
    }
}