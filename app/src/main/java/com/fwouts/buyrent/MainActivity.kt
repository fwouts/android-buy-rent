package com.fwouts.buyrent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fwouts.buyrent.databinding.ActivityMainBinding
import com.fwouts.buyrent.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getString(sectionsPagerAdapter.getTabTitle(position))
        }.attach()
        setContentView(binding.root)
    }
}