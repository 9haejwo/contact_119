package com.android.contact_119.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.contact_119.data.ContactItems
import com.android.contact_119.databinding.ItemLikedBinding
import com.android.contact_119.manager.ContactItemManager

class MyAdapter(private val mItems: MutableList<ContactItems>) :RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    private var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemLikedBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            itemClick?.onClick(it,position)
        }


    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class MyViewHolder(private val binding: ItemLikedBinding):RecyclerView.ViewHolder(binding.root){
        val hospitalimg = binding.imgLogo
        val hospitalname = binding.tvHospital
        val hospitalliked = binding.itemLiked

    }
    private val sortedList = ContactItemManager.sortFavoriteWithHeader()
    val adapter = MyAdapter(sortedList)

}