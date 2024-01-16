package com.android.contact_119.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.api.load
import com.android.contact_119.R
import com.android.contact_119.data.ContactItems
import com.android.contact_119.databinding.ItemContactRecyclerViewBinding
import com.android.contact_119.databinding.ItemHeaderRecyclerViewBinding

private const val TYPE_HEADER = 0
private const val TYPE_CONTENT = 1

class ContactListAdapter(val items: MutableList<ContactItems>) :
    RecyclerView.Adapter<ViewHolder>() {

    inner class HeaderViewHolder(binding: ItemHeaderRecyclerViewBinding) :
        ViewHolder(binding.root) {
        val location = binding.tvLocation
    }

    inner class ContentViewHolder(binding: ItemContactRecyclerViewBinding) :
        ViewHolder(binding.root) {
        val thumbnailImage = binding.ivTumbnail
        val name = binding.tvItemTitle
        val favoriteIcon = binding.ivFavorite
        val favoriteButton = binding.btnFavorite
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ContactItems.Header -> TYPE_HEADER
            is ContactItems.Contents -> TYPE_CONTENT
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(
                ItemHeaderRecyclerViewBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            TYPE_CONTENT -> ContentViewHolder(
                ItemContactRecyclerViewBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        if (item is ContactItems.Header) {
            with(holder as HeaderViewHolder) {
                location.text = item.location
            }
        }

        if (item is ContactItems.Contents) {
            with(holder as ContentViewHolder) {
                name.text = item.itemName
                if (item.thumbnailImage == null) {
                    thumbnailImage.load(R.drawable.main_symbol)
                } else {
                    thumbnailImage.load(item.thumbnailImage!!)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }



}