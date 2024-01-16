package com.example.a115make

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a115make.databinding.ActivityMypageBinding
import com.example.a115make.databinding.ItemBinding

class MyAdapter (val mItems : MutableList<User>) :RecyclerView.Adapter<MyAdapter>.Holder {

    inner class Holder(val binding: ItemBinding):RecyclerView.ViewHolder(binding.root){
        val textView = binding.
    }
}