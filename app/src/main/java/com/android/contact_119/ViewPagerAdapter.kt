package com.android.contact_119

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.Exception

class ViewPagerAdapter(private val mainActivity: MainActivity):FragmentStateAdapter(mainActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
      return when(position){
           0->{
               MainFragment() //연락처 페이지
           }
           1->{
               MyPageFragment() //마이 페이지
           }
          else->throw IllegalStateException("포지션 에러")
       }
    }
}