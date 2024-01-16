package com.android.contact_119.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.android.contact_119.R
import com.android.contact_119.data.ContactItems
import com.android.contact_119.databinding.ItemContactRecyclerViewBinding

class ContactListAdapter(val items: MutableList<ContactItems.Contents>) :
    RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemContactRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val thumbnailImage = binding.ivTumbnail
        val name = binding.tvItemTitle
        val favoriteIcon = binding.ivFavorite
        val favoriteButton = binding.btnFavorite
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemContactRecyclerViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        with(holder) {
            name.text = item.itemName
            if (item.thumbnailImage == null) {
                thumbnailImage.load(R.drawable.main_symbol)
            } else {
                thumbnailImage.load(item.thumbnailImage!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}