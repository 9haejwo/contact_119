package com.android.contact_119

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.contact_119.adapter.ViewPagerAdapter
import com.android.contact_119.databinding.ActivityMainBinding
import com.android.contact_119.manager.UserManager
import com.google.android.material.tabs.TabLayoutMediator

val nowUser = "홍길동"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPagerAdapter = ViewPagerAdapter(this@MainActivity)

        with(binding) {
            //어뎁터 연결
            viewPager.adapter = viewPagerAdapter

            //탭, 뷰페이저 연결
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->

                when (position) {
                    0 -> tab.text = getString(R.string.Tab_Contact)
                    else -> tab.text = getString(R.string.Tab_MyPage)
                }
            }.attach()
        }

    }
}