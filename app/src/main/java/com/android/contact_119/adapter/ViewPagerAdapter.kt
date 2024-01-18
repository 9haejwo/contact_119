package com.android.contact_119.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.contact_119.MainActivity
import com.android.contact_119.MyPageFragment
import com.android.contact_119.fragment.ContactFragment

class ViewPagerAdapter(private val mainActivity: MainActivity) :
    FragmentStateAdapter(mainActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ContactFragment()
            1 -> MyPageFragment()
            else -> throw IllegalStateException("포지션 에러")
        }
    }
}