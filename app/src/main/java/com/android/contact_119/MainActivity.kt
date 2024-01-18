package com.android.contact_119

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.contact_119.adapter.ViewPagerAdapter
import com.android.contact_119.databinding.ActivityMainBinding
import com.android.contact_119.manager.UserManager
import com.google.android.material.tabs.TabLayoutMediator

val nowUser = "홍길동"

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewPagerAdapter by lazy { ViewPagerAdapter(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViewPager()
    }

    private fun initViewPager() {
        with(binding) {
            viewPager.adapter = viewPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.Tab_Contact)
                    else -> tab.text = getString(R.string.Tab_MyPage)
                }
            }.attach()
        }
    }
}